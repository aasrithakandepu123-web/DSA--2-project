class SegTreeLazy {

    long[] tree;
    long[] lazy;
    int n;

    SegTreeLazy(int n) {
        this.n = n;
        tree = new long[4 * n];
        lazy = new long[4 * n];
    }

    // Build tree
    void build(int node, int start, int end, long[] arr) {
        if (start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = (start + end) / 2;

        build(2 * node, start, mid, arr);
        build(2 * node + 1, mid + 1, end, arr);

        tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
    }

    // Push lazy value to children
    void pushDown(int node) {
        if (lazy[node] != 0) {

            tree[2 * node] += lazy[node];
            lazy[2 * node] += lazy[node];

            tree[2 * node + 1] += lazy[node];
            lazy[2 * node + 1] += lazy[node];

            lazy[node] = 0;
        }
    }

    // Range Update
    void updateRange(int node, int lo, int hi,
                     int l, int r, long delta) {

        // No Overlap
        if (r < lo || l > hi)
            return;

        // Full Overlap
        if (l <= lo && hi <= r) {
            tree[node] += delta;
            lazy[node] += delta;
            return;
        }

        // Partial Overlap
        pushDown(node);

        int mid = (lo + hi) / 2;

        updateRange(2 * node, lo, mid, l, r, delta);
        updateRange(2 * node + 1, mid + 1, hi, l, r, delta);

        tree[node] = Math.max(tree[2 * node],
                              tree[2 * node + 1]);
    }

    // Range Maximum Query
    long queryMax(int node, int lo, int hi,
                  int l, int r) {

        // No Overlap
        if (r < lo || l > hi)
            return Long.MIN_VALUE;

        // Full Overlap
        if (l <= lo && hi <= r)
            return tree[node];

        pushDown(node);

        int mid = (lo + hi) / 2;

        long left = queryMax(2 * node, lo, mid, l, r);
        long right = queryMax(2 * node + 1, mid + 1, hi, l, r);

        return Math.max(left, right);
    }

    public static void main(String[] args) {

        int n = 16;

        long[] zones = new long[n];

        // Initially all surge multipliers = 1
        for (int i = 0; i < n; i++) {
            zones[i] = 1;
        }

        SegTreeLazy st = new SegTreeLazy(n);

        st.build(1, 0, n - 1, zones);

        // Operation 1
        st.updateRange(1, 0, 15, 3, 9, 5);

        // Operation 2
        st.updateRange(1, 0, 15, 7, 14, 3);

        // Operation 3
        System.out.println("Max [0,15] = "
                + st.queryMax(1, 0, 15, 0, 15));

        // Operation 4
        st.updateRange(1, 0, 15, 2, 6, 7);

        // Operation 5
        System.out.println("Max [4,10] = "
                + st.queryMax(1, 0, 15, 4, 10));
    }
}