package cn.wlh.util.base.adapter.java.lang;

import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author �����
 *  �ɱ����飬Ҳ����һ�����ϡ�
 *  ��һ��Ӧ�ó�����
 *  ������������Ҫ���ǡ���add����֮����ȷ��������Ҫadd�ˣ���������ֻ�ǲ�ѯ�����޸��ˡ�����Ҫ
 *  @see cn.wlh.util.base.adapter.java.lang.AddAfterSeeImp<E>
 * @param <E>
 */
public class VariableArray<E> implements List<E> {
	int size;
	//��������Ϊ�ɱ䡣
	VariableArray<E> next;
	E value ;
	//���ձ���Ϊ���顣
	Object [] array; 
	
	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0 ? true : false;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		
		return null;
	}

	@Override
	public Object[] toArray() {
//		E [] es = new E[size];
		Object [] ss = new Object [ size ];
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public VariableArray<E> getArray(int index){
		if( index == 0 ) return this;
		VariableArray<E> temp = this;
		for (int i = 1; i <= index; i++) {
			temp = temp.next; // �¸�����ʱ
		}
		return temp;
	}
	
	@Override
	public boolean add(E e) {
		// add��׷�ӵ���һ.������������߶����һ��LastNode
		// ������Ҫ������ʱ��Ӻ���ǰ������
		E e2 = this.get( size -1 );
		size ++;
		return false;
	}
	
	
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, E element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	public class Itr implements Iterator<E> {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         */
        int lastRet = -1;
		
		@Override
		public boolean hasNext() {
			return cursor != size();
		}

		@Override
		public E next() {
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
		}


    
	}
}
