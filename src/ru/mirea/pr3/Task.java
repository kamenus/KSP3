package ru.mirea.pr3;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Task {
    public PrintWriter out;
    public BufferedReader in;
    public Socket socket;
    public boolean flag;
    public String task;
    public int result;

    public Task(PrintWriter out, BufferedReader in, Socket socket, String task) {
        this.out = out;
        this.in = in;
        this.socket = socket;
        this.task = task;
        this.result = 0;
        this.flag = false;
    }
}
