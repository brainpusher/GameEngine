package textures;

public class ModelTexture {

    private int textureID;

    private float shineDamper = 1; // in what range our camera will see the coming specular light
    private float reflectivity = 0;//how strong our light reflect from the surface

    public ModelTexture(int id){
        this.textureID = id;
    }

    public int getID(){
        return  this.textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
