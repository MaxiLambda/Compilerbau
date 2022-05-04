package com.thecout.lox.Parser.Expr;

public class LList extends Expr{
    final Expr current;
    final Expr next;

    public LList(Expr current) {
        this(current, null);
    }

    public LList(Expr current, Expr next) {
        this.current = current;
        this.next = next;
    }

    public boolean hasNext(){
        return null == next;
    }

    @Override
    public String print() {
        return null;
    }


}
