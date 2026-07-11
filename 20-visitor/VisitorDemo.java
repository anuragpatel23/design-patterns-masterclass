package visitor;

import java.util.List;

/**
 * Visitor — apply operations (area, describe) to a shape hierarchy via double dispatch.
 * Run: javac VisitorDemo.java && java visitor.VisitorDemo
 */
public class VisitorDemo {

    interface ShapeVisitor {
        double visit(Circle c);
        double visit(Rectangle r);
    }
    interface Shape { double accept(ShapeVisitor v); }

    static class Circle implements Shape {
        final double radius; Circle(double r) { radius = r; }
        public double accept(ShapeVisitor v) { return v.visit(this); } // double dispatch
    }
    static class Rectangle implements Shape {
        final double w, h; Rectangle(double w, double h) { this.w = w; this.h = h; }
        public double accept(ShapeVisitor v) { return v.visit(this); }
    }

    // A new operation = a new visitor; shapes are untouched.
    static class AreaVisitor implements ShapeVisitor {
        public double visit(Circle c)    { return Math.PI * c.radius * c.radius; }
        public double visit(Rectangle r) { return r.w * r.h; }
    }

    public static void main(String[] args) {
        List<Shape> shapes = List.of(new Circle(2), new Rectangle(3, 4));
        AreaVisitor area = new AreaVisitor();
        double total = 0;
        for (Shape s : shapes) total += s.accept(area);
        System.out.printf("Total area = %.2f%n", total);
    }
}
