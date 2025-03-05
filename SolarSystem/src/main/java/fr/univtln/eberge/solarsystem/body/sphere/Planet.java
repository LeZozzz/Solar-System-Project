package fr.univtln.eberge.solarsystem.body.sphere;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.BufferUtils;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.material.RenderState;

public class Planet {
    private String name;
    private float size;
    private float rotationPeriod;
    private float revolutionPeriod;
    private Node orbitNode;
    private Node planetNode;
    private Geometry geometry;
    private float distanceFromSun;
    private float semiMajorAxis;   // Demi-grand axe
    private float eccentricity;    // Excentricité
    private float currentAngle = 0;
        
    
        public Planet(String name, float size, float eccentricity, float semiMajorAxis, float rotationPeriod, float revolutionPeriod, String texturePath, AssetManager assetManager) {
        this.name = name;
        this.size = size;
        this.semiMajorAxis = semiMajorAxis;
        this.eccentricity = eccentricity;
        // this.distanceFromSun = distanceFromSun;
        this.rotationPeriod = rotationPeriod;
        this.revolutionPeriod = revolutionPeriod;

        orbitNode = new Node(name + "_orbitNode");
        planetNode = new Node(name + "_planetNode");
        orbitNode.attachChild(planetNode);

        Sphere sphere = new Sphere(32, 32, size);
        geometry = new Geometry(name, sphere);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        geometry.setMaterial(material);

        planetNode.attachChild(geometry);
        // planetNode.setLocalTranslation(new Vector3f(distanceFromSun + 109f, 0, 0));
    }

    public Node getOrbitNode() {
        return orbitNode;
    }

    public Node getPlanetNode() {
        return planetNode;
    }

    public float getRotationPeriod() {
        return rotationPeriod;
    }

    public float getRevolutionPeriod() {
        return revolutionPeriod;
    }

    public Node getBodyNode() {
        return planetNode;
    }
    public void setLocalRotation(Quaternion quaternion) {
        this.geometry.setLocalRotation(quaternion);
    }

    public String getName() {
        return name;
    }
    public float getSize() {
        return size;
    }

    public float getRotationSpeed() {
        return 360 / rotationPeriod;
    }
    public float getRevolutionSpeed() {
        return 360 / revolutionPeriod;
    }

    public Vector3f getLocalTranslation() {
        return planetNode.getLocalTranslation();
    }

    // Modification en cour des trajectoires
    public float getSemiMajorAxis() { 
        return semiMajorAxis; 
    }
    public float getEccentricity() { return eccentricity; }
    public float getCurrentAngle() { return currentAngle; }
    public void setCurrentAngle(float currentAngle) { this.currentAngle = currentAngle; }

    public float getAngle(double time){
        return (float)(((FastMath.TWO_PI / (revolutionPeriod*365*24*60*60)) * time) % FastMath.TWO_PI);
    }
    public Vector3f calcTrajectory(double time){
        float focalDistance = semiMajorAxis * eccentricity;
        float angle = getAngle(time);
        float x = semiMajorAxis * FastMath.cos(angle) - focalDistance; // Centré sur le foyer
        float z = semiMajorAxis * FastMath.sqrt(1 - eccentricity * eccentricity) * FastMath.sin(angle);
        return new Vector3f(x, 0, z);
    }


    public static Geometry createPlanetRings(String texturePath, AssetManager assetManager) {
            int segments = 64;
            float innerRadius = 90.14f * 1.236145f;
            float outerRadius = 90.14f * 2.27318f;
    
            Mesh ringMesh = new Mesh();
            Vector3f[] vertices = new Vector3f[segments * 2];
            Vector2f[] texCoords = new Vector2f[segments * 2];
            int[] indices = new int[segments * 6];
    
            for (int i = 0; i < segments; i++) {
                float angle = (float) (i * Math.PI * 2 / segments);
                float cos = (float) Math.cos(angle);
                float sin = (float) Math.sin(angle);
    
                // Positions des sommets
                vertices[i * 2] = new Vector3f(innerRadius * cos, 0, innerRadius * sin);
                vertices[i * 2 + 1] = new Vector3f(outerRadius * cos, 0, outerRadius * sin);
    
                // Mapping UV correct :
                texCoords[i * 2] = new Vector2f(0, i / (float) segments); // Intérieur (gauche de l’image)
                texCoords[i * 2 + 1] = new Vector2f(1, i / (float) segments); // Extérieur (droite de l’image)
    
                int next = (i + 1) % segments;
    
                // Triangles pour le disque
                indices[i * 6] = i * 2;
                indices[i * 6 + 1] = next * 2;
                indices[i * 6 + 2] = i * 2 + 1;
    
                indices[i * 6 + 3] = i * 2 + 1;
                indices[i * 6 + 4] = next * 2;
                indices[i * 6 + 5] = next * 2 + 1;
            }
            ringMesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
            ringMesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
            ringMesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indices));

            ringMesh.updateBound();

            Geometry ringGeo = new Geometry(texturePath, ringMesh);
            Material ringMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            ringMat.setTexture("ColorMap", assetManager.loadTexture(texturePath));

            ringMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            ringMat.getAdditionalRenderState().setDepthWrite(true);
            ringMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

            ringGeo.setMaterial(ringMat);
            ringGeo.setQueueBucket(RenderQueue.Bucket.Transparent);

            return ringGeo;
    }
}