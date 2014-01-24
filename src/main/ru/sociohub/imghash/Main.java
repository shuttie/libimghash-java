package ru.sociohub.imghash;

/**
 * Created by shutty on 1/24/14.
 */
public class Main {
    public static void main(String[] args) {
        String hex = imghash.imghash_file_hex("/tmp/apple.jpg");
        System.out.print(hex);
    }
}
