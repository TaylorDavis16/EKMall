package domain;

import java.io.Serializable;

public class Cart implements Serializable {
    private int cid;
    private int mid;
    private int number;

    public Cart(int cid, int mid, int number) {
        this.cid = cid;
        this.mid = mid;
        this.number = number;
    }

    public Cart() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", mid=" + mid +
                ", number=" + number +
                '}';
    }
}
