package client;

public class Launch {
    public static void main(String[] args) {
        String[] arg = {"--enable-preview", "--module-path %PATH_TO_FX%", "--add-modules=javafx.controls,javafx.fxml"};
        Client.main(arg);
    }
}
