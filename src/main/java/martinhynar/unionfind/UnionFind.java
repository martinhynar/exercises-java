package martinhynar.unionfind;

public interface UnionFind {

    public void union(int item, int other);

    public int find(int item);

    public boolean isConnected(int item, int other);

    public int getComponents();
}
