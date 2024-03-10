import java.io.*;
import java.nio.charset.StandardCharsets;

public class Player {
    private String name;
    private int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void writeToFile() {
        if (name == null) {
            return;
        }
        try (FileOutputStream outputStream = new FileOutputStream("players.bin", true)) {
            byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);

            outputStream.write(nameBytes.length);
            outputStream.write(nameBytes);
//            outputStream.write((score << 24));
//            score = score - score << 24;
//            outputStream.write((score << 16));
//            outputStream.write((score << 8));
//            outputStream.write((score >> 24));
            for (int i = 3; i >=0; i--) {
                outputStream.write((score >> i*8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void writeToFile() {
        if (name == null) {
            return;
        }
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("players.bin", true))) {

            outputStream.writeByte(name.length());
            outputStream.writeBytes(name);
            outputStream.writeInt(score);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}