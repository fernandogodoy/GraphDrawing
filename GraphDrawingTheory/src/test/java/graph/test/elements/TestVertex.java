package graph.test.elements;

import java.awt.Dimension;

import graph.elements.Vertex;

public class TestVertex implements Vertex{

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestVertex other = (TestVertex) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}


	private String content;
	public double minDistance = Double.POSITIVE_INFINITY;
	private Dimension size;
	
	public TestVertex(){
		
	}
	
	public TestVertex(String content) {
		this.content = content;
	}

	
	@Override
	public Dimension getSize() {
		return size;
	}
	
	public void setSize(Dimension size) {
		this.size = size;
	}
	

	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public Object getContent() {
		return content;
	}


	@Override
	public String toString() {
		return content;
	}


	@Override
	public void setContent(Object content) {
		// TODO Auto-generated method stub
		
	}



}
