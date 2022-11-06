package noughtsandcrosses;

public enum GridMapping {

    TL("Top left", 0, 0),
    TM("Top middle", 0, 1),
    TR("Top right", 0, 2),
    ML("Middle left", 1, 0),
    MM("Middle middle", 1, 1),
    MR("Middle right", 1, 2),
    BL("Bottom left", 2, 0),
    BM("Bottom middle", 2, 1),
    BR("Bottom Right", 2, 2);

    private final String name;
    private final int  xCoordinate;
    private final int  yCoordinate;

    GridMapping(String name, int xCoordinate, int yCoordinate) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public String getName() {
        return name;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }
}
