package martinhynar.unionfind;


import java.util.Arrays;

public class WeightedUnionFind implements UnionFind {
    int[] parent;
    int[] componentSize;
    int components;

    public WeightedUnionFind(int size) {
        parent = new int[size + 1];
        componentSize = new int[size + 1];
        parent[0] = -1;
        componentSize[0] = -1;
        components = size;
        for (int i = 1; i <= size; i++) {
            // Item is own parent
            parent[i] = i;
            // Each item is in separate component of size 1
            componentSize[i] = 1;
        }
    }

    @Override
    public void union(int item, int other) {
        int rootA = find(item);
        int rootB = find(other);
        if (rootA != rootB) {
            if (componentSize[rootA] >= componentSize[rootB]) {
                parent[rootB] = rootA;
                componentSize[rootA] += componentSize[rootB];
            } else {
                parent[rootA] = rootB;
                componentSize[rootB] += componentSize[rootA];
            }
            components--;
//            System.out.printf("Joining %d-%d (components = %d)%n", item, other, components);
        }
    }

    @Override
    public int find(int item) {
        int root = item;
        while (parent[root] != root) {
            root = parent[root];
        }
        return root;
    }

    @Override
    public int getComponents() {
        return components;
    }

    @Override
    public boolean isConnected(int item, int other) {
        return find(item) == find(other);
    }


    @Override
    public String toString() {
        return String.format("%s", Arrays.toString(parent));
    }
}
