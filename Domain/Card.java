package Domain;
public class Card{
    private int value, id;
    public boolean avaliable = true;
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getId(){
        return id;
    }
    public void userCard(int randomNumber){
        switch(randomNumber % 54){
            case 0: value = 0; id = 41; break;
            case 1: value = 1; id = 0; break;
            case 2: value = 1; id = 0; break;
            case 3: value = 1; id = 0; break;
            case 4: value = 2; id = 1; break;
            case 5: value = 2; id = 1; break;
            case 6: value = 2; id = 1; break;
            case 7: value = 3; id = 2; break;
            case 8: value = 3; id = 2; break;
            case 9: value = 3; id = 2; break;
            case 10: value = 4; id = 3; break;
            case 11: value = 4; id = 3; break;
            case 12: value = 4; id = 3; break;
            case 13: value = 5; id = 4; break;
            case 14: value = 5; id = 4; break;
            case 15: value = 5; id = 4; break;
            case 16: value = 6; id = 5; break;
            case 17: value = 6; id = 5; break;
            case 18: value = 6; id = 5; break;
            case 19: value = -1; id = 12; break;
            case 20: value = -1; id = 12; break;
            case 21: value = -1; id = 12; break;
            case 22: value = -2; id = 13; break;
            case 23: value = -2; id = 13; break;
            case 24: value = -2; id = 13; break;
            case 25: value = -3; id = 14; break;
            case 26: value = -3; id = 14; break;
            case 27: value = -3; id = 14; break;
            case 28: value = -4; id = 15; break;
            case 29: value = -4; id = 15; break;
            case 30: value = -4; id = 15; break;
            case 31: value = -5; id = 16; break;
            case 32: value = -5; id = 16; break;
            case 33: value = -5; id = 16; break;
            case 34: value = -6; id = 17; break;
            case 35: value = -6; id = 17; break;
            case 36: value = -6; id = 17; break;
            case 37: value = 1; id = 24; break;
            case 38: value = 1; id = 24; break;
            case 39: value = 2; id = 25; break;
            case 40: value = 2; id = 25; break;
            case 41: value = 3; id = 26; break;
            case 42: value = 3; id = 26; break;
            case 43: value = 4; id = 27; break;
            case 44: value = 4; id = 27; break;
            case 45: value = 5; id = 28; break;
            case 46: value = 5; id = 28; break;
            case 47: value = 6; id = 29; break;
            case 48: value = 6; id = 29; break;
            case 49: value = 1; id = 36; break;
            case 50: value = 12; id = 42; break;
            case 51: value = 24; id = 43; break;
            case 52: value = 36; id = 44; break;
            case 53: value = 25; id = 45; break;
        }
    }
    public void tableCards(int randomNumber){
        switch(randomNumber % 23){
            case 0: value = 1; id = 46; break;
            case 1: value = 1; id = 46; break;
            case 2: value = 2; id = 47; break;
            case 3: value = 2; id = 47; break;
            case 4: value = 3; id = 48; break;
            case 5: value = 3; id = 48; break;
            case 6: value = 4; id = 49; break;
            case 7: value = 4; id = 49; break;
            case 8: value = 4; id = 49; break;
            case 9: value = 5; id = 50; break;
            case 10: value = 5; id = 50; break;
            case 11: value = 5; id = 50; break;
            case 12: value = 6; id = 51; break;
            case 13: value = 6; id = 51; break;
            case 14: value = 6; id = 51; break;
            case 15: value = 7; id = 52; break;
            case 16: value = 7; id = 52; break;
            case 17: value = 7; id = 52; break;
            case 18: value = 8; id = 53; break;
            case 19: value = 8; id = 53; break;
            case 20: value = 9; id = 54; break;
            case 21: value = 9; id = 54; break;
            case 22: value = 10; id = 55; break;
        }
    }
}
