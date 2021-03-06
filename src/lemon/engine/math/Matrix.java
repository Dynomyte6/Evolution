package lemon.engine.math;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

public class Matrix {
	private float[][] data;
	
	public Matrix(int size){
		this(size, size);
	}
	public Matrix(int m, int n){
		this.data = new float[m][n];
	}
	public void set(int m, int n, float data){
		this.data[m][n] = data;
	}
	public float get(int m, int n){
		return data[m][n];
	}
	public int getRows(){
		return data.length;
	}
	public int getColumns(){
		return data[0].length;
	}
	public FloatBuffer toFloatBuffer(){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length*data[0].length);
		for(int j=0;j<this.getColumns();++j){
			for(int i=0;i<this.getRows();++i){
				buffer.put(this.get(i, j));
			}
		}
		buffer.flip();
		return buffer;
	}
	public Matrix multiply(Matrix matrix){
		if(getColumns()!=matrix.getRows()){
			throw new IllegalArgumentException("You cannot multiply "+getRows()+" x "+getColumns()+" by "+matrix.getRows()+" x "+matrix.getColumns());
		}
		Matrix product = new Matrix(getRows(), matrix.getColumns());
		for(int i=0;i<getRows();++i){
			for(int j=0;j<matrix.getColumns();++j){
				float sum = 0;
				for(int k=0;k<matrix.getRows();++k){
					sum+=get(i, k)*matrix.get(k, j);
				}
				product.set(i, j, sum);
			}
		}
		return product;
	}
	@Override
	public String toString(){
		return Arrays.deepToString(data);
	}
	public static Matrix getIdentity(int size){
		Matrix matrix = new Matrix(size);
		for(int i=0;i<size;++i){
			matrix.set(i, i, 1);
		}
		return matrix;
	}
}
