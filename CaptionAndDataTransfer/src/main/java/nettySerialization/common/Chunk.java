package nettySerialization.common;

public enum Chunk {
    CHUNK_SIZE(1024*1024*256);
    //CHUNK_SIZE(10*1024*1024);

    private int sizeBytes;

    Chunk(int size) {
        this.sizeBytes = size;
    }

    public int getSizeBytes() {
        return sizeBytes;
    }
}
