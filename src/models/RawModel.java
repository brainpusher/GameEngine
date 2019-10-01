package models;

public class RawModel {
    private int vaoID;//vertex array object id
    private int vertexCount;

    public RawModel(int vaoID,int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }
    public int getVertexCount() {
        return vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }
}
