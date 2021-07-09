import java.util.ArrayList;

class WordArray extends ArrayList {

    WordArray() {
    }

    //Returns the current list without the square brackets present in .toString() method
    String trimmedToString() {
        String temp = this.toString();
        return temp.substring(1, (temp.length() - 1));
    }
}
