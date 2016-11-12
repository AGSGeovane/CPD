import Controller.SortController;

/**
 * Created by Geovane on 17/08/2016.
 */
public class Main {

    public void startSort() throws Exception {
        SortController controller = new SortController();
        controller.start();
    }

    public static void main(String args[]) throws Exception {
        Main caller = new Main();
        caller.startSort();
    }


}
