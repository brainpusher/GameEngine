package renderEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


//this class will load 3d model
public class Loader {
    private static final int COUNT_OF_DIMENSIONS_IN_POSITION_COORDINATE = 3;//x,y,z = 3 dimensions
    private static final int COUNT_OF_DIMENSIONS_IN_NORMALS_VECTOR = 3;//x,y,z = 3 dimensions
    private static final int COUNT_OF_DIMENSIONS_IN_TEXTURE_COORDINATE = 2;//x,y = 2 dimensions
    private static final int INDEX_OF_VBO_IN_VAO_FOR_POSITIONS = 0;
    private static final int INDEX_OF_VBO_IN_VAO_FOR_TEXTURE_COORDINATE = 1;
    private static final int INDEX_OF_VBO_IN_VAO_FOR_NORMALS = 2;

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions,float[] textureCoord, float[] normals, int[] indexes){
        int vaoID = createVAO();
        bindIndexesBuffer(indexes);
        storeDataInAttributeList(INDEX_OF_VBO_IN_VAO_FOR_POSITIONS, COUNT_OF_DIMENSIONS_IN_POSITION_COORDINATE, positions);
        storeDataInAttributeList(INDEX_OF_VBO_IN_VAO_FOR_TEXTURE_COORDINATE,COUNT_OF_DIMENSIONS_IN_TEXTURE_COORDINATE,textureCoord);
        storeDataInAttributeList(INDEX_OF_VBO_IN_VAO_FOR_NORMALS,COUNT_OF_DIMENSIONS_IN_NORMALS_VECTOR,normals);
        unbindVAO();
        return new RawModel(vaoID,indexes.length);
    }



    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays(); //create empty vao and returns its id
        vaos.add(vaoID);
        System.out.println(vaoID);
        GL30.glBindVertexArray(vaoID);//if we want to activate something we bind it
        return vaoID;
    }
    //store data in attribute list in vao
    private void storeDataInAttributeList(int attributeNumber,int coordinateSize, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboID);//we want to bind vbo id
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW); //(specify type of the buffer, buffer,what is it used for)

        //1 arg = number of position where we want to data
        //2 arg = number of dimension of point ( we have a three dimensional point x,y,z)
        //3 arg = type of data
        //4 arg = if our data is normalized (false in my case)
        //5 arg = distance between each of my vertexes
        //6 arg = offset
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);//unbind vbo
    }

    private void unbindVAO(){
        GL30.glBindVertexArray(0);//0 means unbind
    }

    private void bindIndexesBuffer(int[] indexes){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,vboID);//we want to bind vbo id to use it
        IntBuffer buffer = storeDataInIntBuffer(indexes);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //(specify type of the buffer, buffer,what is it used for)
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer  buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();//means that we done writing to buffer and we r ready to read from buffer
        return buffer;
    }

    public int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (texture != null) {
            int textureID = texture.getTextureID();
            textures.add(textureID);
            return textureID;
        }
        return -1;
    }

    public void cleanUp(){
        for(int vao:vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos){
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture:textures){
            GL11.glDeleteTextures(texture);
        }
    }
}
