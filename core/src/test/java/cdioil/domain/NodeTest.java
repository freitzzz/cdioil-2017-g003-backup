/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class NodeTest {

    public NodeTest() {
    }

    /**
     * Test of empty constructor of class Node
     */
    @Test
    public void testEmptyConstructor() {
        System.out.println("Node()");
        Node node = new Node();
    }

    /**
     * Test of getParent method, of class Node.
     */
    @Test
    public void testGetParent() {
        System.out.println("getParent");
        Category element = new Category("Name", "10DC");
        Node node = new Node(element);
        assertNull(node.getParent());

        Category element1 = new Category("Anotha one", "11DC");
        Node child = new Node(element1);
        assertNull(child.getParent());
        node.addChild(child);
        assertEquals(child.getParent(), node);
        assertTrue(child.getParent() == node);

        //Despite fakeParent and the child's parent being equal, fakeParent is not the child's actual parent
        Node fakeParent = new Node(element);
        assertEquals(fakeParent, child.getParent());
    }

    /**
     * Test of getElement method, of class Node.
     */
    @Test
    public void testGetElement() {
        System.out.println("getElement");
        Category c = new Category("Bajoras", "15DC");
        Node node = new Node(c);

        Category expected = new Category("Bajoras", "15DC");
        assertEquals(expected, node.getElement());
    }

    /**
     * Test of getChildren method, of class Node.
     */
    @Test
    public void testGetChildren() {
        System.out.println("getChildren");

        Category c = new Category("Bajoras", "15DC");
        Node node = new Node(c);
        assertNotNull(node.getChildren());
        assertTrue(node.getChildren().isEmpty());

        node.addChild(new Node(new Category("Bajoras", "15DC")));

        assertEquals(1, node.getChildren().size());
    }

    /**
     * Test of addChild method, of class Node.
     */
    @Test
    public void testAddChild() {
        System.out.println("addChild");

        Category c = new Category("Bajoras", "15DC");
        Node node = new Node(c);

        assertFalse(node.addChild(null));

        Category c2 = new Category("Oh he need some milk","15DC-12UN");

        Node child = new Node(c2);
        assertTrue(node.addChild(child));

        assertEquals(node, child.getParent());
        assertTrue(node == child.getParent());

        assertFalse(node.addChild(new Node(c2)));
    }

    /**
     * Test of hashCode method, of class Node.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Category c = new Category("Bajoras", "15DC");
        Node node = new Node(c);

        Node node2 = new Node(c);

        assertEquals(node.hashCode(), node2.hashCode());

    }

    /**
     * Test of equals method, of class Node.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        Category c = new Category("Bajoras", "15DC");
        Node node = new Node(c);

        Node node2 = new Node(c);

        assertEquals(node, node2);

        assertNotEquals(node, null);

        assertNotEquals(node, new EAN("424242342"));

    }

    /**
     * Test of toString method, of class Node.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        Category c = new Category("Bajoras", "15DC");
        Node node = new Node(c);

        Node node2 = new Node(c);

        assertEquals(node.toString(), node2.toString());

    }

}
