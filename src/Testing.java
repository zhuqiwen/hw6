/**
 * Created by Qiwen Zhu on 4/3/17.
 */



import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.AssertionError;

import org.junit.Test;


public class Testing
{

	@Test
	public void noEdgeTest()
	{
		ListDigraph gr = new ListDigraph(3);
		assertTrue(!gr.hasEdge(0 ,1));
		assertTrue(!gr.hasEdge(0 ,2));
		assertTrue(!gr.hasEdge(1 ,0));
		assertTrue(!gr.hasEdge(1 ,2));
		assertTrue(!gr.hasEdge(2 ,1));
		assertTrue(!gr.hasEdge(2 ,0));
	}

	@Test
	public void hasEdgeTest()
	{
		ListDigraph gr = new ListDigraph(3);
		gr.addEdge(0, 1, 2);
		assertTrue(gr.hasEdge(0 ,1));
		assert 2 == gr.weight(0, 1);

		gr.addEdge(0, 2, 4);
		assertTrue(gr.hasEdge(0 ,2));
		assert 4 == gr.weight(0, 2);

		gr.addEdge(1, 2, 3);
		assertTrue(gr.hasEdge(1 ,2));
		assert 2 != gr.weight(1, 2);
		assert 3 == gr.weight(1, 2);

		gr.addEdge(0, 0, 2);
		assertTrue(gr.hasEdge(0 ,0));
		assert 2 == gr.weight(0, 0);
	}


	@Test
	public void weightTest()
	{
		ListDigraph gr = new ListDigraph(3);

		try
		{
			gr.weight(0, 1);
		} catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage(), is("there is no edge from 0 to 1"));
		}

		gr.addEdge(0, 1, 1);
		assert 1 == gr.weight(0, 1);

	}

	@Test
	public void numVerticesTest()
	{
		ListDigraph gr = new ListDigraph(3);
		assert 3 == gr.numVertices();

		try
		{
			gr = new ListDigraph(-4);
		} catch (AssertionError e)
		{
			assertThat(e, isA(java.lang.AssertionError.class));
		}

	}

	@Test
	public void numEdgesTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		gr.addEdge(3, 6, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

		assert 12 == gr.numEdges();

	}

	@Test
	public void addEdgeTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		assert 2 == gr.weight(0, 1);

		gr.addEdge(0, 1, 4);
		assert 4 == gr.weight(0, 1);
	}

	@Test
	public void outAndInTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		gr.addEdge(3, 6, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

		assert gr.out(0).toString().equals("[1, 2, 3]");
		assert !gr.out(0).toString().equals("[1, 2, 3, 4]");

		assert gr.in(0).toString().equals("[]");
		assert gr.in(1).toString().equals("[0]");
		assert gr.in(5).toString().equals("[2, 3, 6]");
	}

	@Test
	public void twoHopsAwayTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		gr.addEdge(3, 6, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

		assert "[2, 3, 4, 5, 6]".equals(gr.twoHopsAway(0).toString());

		gr.addEdge(0,0);

		assert "[0, 1, 2, 3, 4, 5, 6]".equals(gr.twoHopsAway(0).toString());


	}
}