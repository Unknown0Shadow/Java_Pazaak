class Card {
    private int value;
    private int id;

    int getValue() {
        return value;
    }
    void setValue(int value) {
        this.value = value;
    }
    int getId(){
        return id;
    }
    void userCard(int randomNumber){
        switch (randomNumber % 54) {
            case 1: value = 1; id = 0; break;
            case 2: value = 1; id = 0; break;
            case 3: value = 2; id = 1; break;
            case 4: value = 2; id = 1; break;
            case 5: value = 3; id = 2; break;
            case 6: value = 3; id = 2; break;
            case 7: value = 4; id = 3; break;
            case 8: value = 4; id = 3; break;
            case 9: value = 5; id = 4; break;
            case 10: value = 5;id = 4; break;
            case 11: value = 6; id = 5; break;
            case 12: value = 6; id = 5; break;
            case 13: value = -1; id = 6; break;
            case 14: value = -1; id = 6; break;
            case 15: value = -2; id = 7; break;
            case 16: value = -2; id = 7; break;
            case 17: value = -3; id = 8; break;
            case 18: value = -3; id = 8; break;
            case 19: value = -4; id = 9; break;
            case 20: value = -4; id = 9; break;
            case 21: value = -5; id = 10; break;
            case 22: value = -5; id = 10; break;
            case 23: value = -6; id = 11; break;
            case 24: value = -6; id = 11; break;
            case 25: value = 1; id = 18; break;
            case 26: value = 1; id = 18; break;
            case 27: value = 2; id = 19; break;
            case 28: value = 2; id = 19; break;
            case 29: value = 3; id = 20; break;
            case 30: value = 3; id = 20; break;
            case 31: value = 4; id = 21; break;
            case 32: value = 4; id = 21; break;
            case 33: value = 5; id = 22; break;
            case 34: value = 5; id = 22; break;
            case 35: value = 6; id = 23; break;
            case 36: value = 6; id = 23; break;
            case 37: value = 102; id = 12; break;
            case 38: value = 12; id = 13; break;
            case 39: value = 24; id = 14; break;
            case 40: value = 36; id = 15; break;
            case 41: value = 25; id = 16; break;
            case 42: value = 1; id = 0; break;
            case 43: value = 2; id = 1; break;
            case 44: value = 3; id = 2; break;
            case 45: value = 4; id = 3; break;
            case 46: value = 5; id = 4; break;
            case 47: value = 6; id = 5; break;
            case 48: value = -1; id = 6; break;
            case 49: value = -2; id = 7; break;
            case 50: value = -3; id = 8; break;
            case 51: value = -4; id = 9; break;
            case 52: value = -5; id = 10; break;
            case 53: value = -6; id = 11; break;
            case 0: value = 0; id = 17; break;
        }
    }
    void tableCards(int randomNumber){
        switch (randomNumber % 23) {
            case 1: value = 1; id = 30; break;
            case 2: value = 1; id = 30; break;
            case 3: value = 2; id = 31; break;
            case 4: value = 2; id = 31; break;
            case 5: value = 3; id = 32; break;
            case 6: value = 3; id = 32; break;
            case 7: value = 4; id = 33; break;
            case 8: value = 4; id = 33; break;
            case 9: value = 4; id = 33; break;
            case 10:value = 5; id = 34; break;
            case 11:value = 5; id = 34; break;
            case 12:value = 5; id = 34; break;
            case 13:value = 6; id = 35; break;
            case 14:value = 6; id = 35; break;
            case 15:value = 6; id = 35; break;
            case 16:value = 7; id = 36; break;
            case 17:value = 7; id = 36; break;
            case 18:value = 7; id = 36; break;
            case 19:value = 8; id = 37; break;
            case 20:value = 8; id = 37; break;
            case 21:value = 9; id = 38; break;
            case 22:value = 9; id = 38; break;
            case 0: value = 10; id = 39; break;
        }
    }
    public String toString(){
        return String.format("%d",value);
    }
}
