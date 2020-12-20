package ru.mirea.pr3;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

public class TaskThread extends Thread {

    private ConcurrentHashMap<SocketChannel, ByteBuffer> tasks = null;

    private void closeSocket(SocketChannel socketChannel) {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public TaskThread(ConcurrentHashMap<SocketChannel, ByteBuffer> tasks) {
        this.tasks = tasks;
    }

    public void Calculator(ByteBuffer byteBuffer){
        String v = new String(byteBuffer.array() , Charset.forName("UTF-8") );
        String[] arr = v.replace("\n","").split(" ", 3);
        int a = Integer.parseInt(arr[0]);
        int b = Integer.parseInt(arr[2]);
        switch (arr[1]) {
            case "+":
                byteBuffer.put(ByteBuffer.allocate(a+b));
                break;
            case "-":
                byteBuffer.put(ByteBuffer.allocateDirect(a-b));
                break;
            case "*":
                byteBuffer.put(ByteBuffer.allocateDirect(a*b));
                break;
            case "/":
                byteBuffer.put(ByteBuffer.allocateDirect(a/b));
                break;
            default:
                System.err.println("Incorrect data");
        }
    }

    public void run() {
        while (true){
            // Iterate through sockets to see f any socket has anything to say
            tasks.forEach((socketCh, byteBuffer) -> {
                try {
                    int data = socketCh.read(byteBuffer);
                    if (data == -1) {
                        closeSocket(socketCh);
                    } else if (data != 0) { // 0 means socket has nothing to say
                        byteBuffer.flip();
                        System.out.println(byteBuffer);
                        Calculator(byteBuffer);
                        while (byteBuffer.hasRemaining()) {
                            socketCh.write(byteBuffer);
                        }
                        byteBuffer.compact();
                    }
                } catch (IOException e) {
                    closeSocket(socketCh);
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

}