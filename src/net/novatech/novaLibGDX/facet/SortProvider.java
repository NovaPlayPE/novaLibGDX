package net.novatech.novaLibGDX.facet;

public enum SortProvider {
	
	TILE {
		public int compare(Facet a, Facet b) {
			if (b.provider == TILE) {
				if (a.getLayer() == b.getLayer())
					return 0;
				return a.getLayer() < b.getLayer() ? 1 : -1;
			} else {
				return -1;
			}

		}
	},
	
	OBJECT {
		public int compare(Facet a, Facet b) {
			if (b.provider == OBJECT) {
				if (a.getLayer() == b.getLayer())
					return 0;
				return a.getLayer() < b.getLayer() ? 1 : -1;
			} else {
				return 1;
			}

		}
	};

	public static final float shadow = -999999;
	public static final float light = 999999;
	public static final float dark = 9999999;

	public abstract int compare(Facet a, Facet b);
}
