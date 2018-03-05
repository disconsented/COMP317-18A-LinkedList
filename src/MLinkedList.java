import java.util.*;

public class MLinkedList<Object extends Comparable> implements List{
    private int total;
    private Node<Object> first;

    public void qSort(){
        if(size() > 1){
            //Return the right most element to use as the head
            Comparable head = remove(size()-1);

            //Create the new small and large lists
            MLinkedList small = new MLinkedList();
            MLinkedList large = new MLinkedList();

            //Iterate through all of the remaining elements sorting them into either list
            Node current = first;
            for (int i = 0; i < size(); i++) {
                Comparable comparable = current.getElement();

                //Get the next node and unlink the old node without destorying it
                Node old = current;
                current = current.getNextNode();
                old.setNextNode(null);

                //Append the node to avoid creating new objects
                if(head.compareTo(comparable) > 0){
                    small.append(old);
                } else {
                    large.append(old);
                }
            }

            //Sort the lists without the head element to avoid infinite recursion
            small.qSort();
            large.qSort();

            //Append the head and then the large list to the small one
            small.append(head);
            small.append(large);

            //Set the parent list to be the small one
            first = small.first;
            total = small.total;
        }
    }

    /**
     * Quicksort using the Hoare Partition scheme
     * @param a The MLinkedList to sort
     * @param low Low side to start from
     * @param high High side to start from
     * @return The sorted list
     */
    public static MLinkedList quickSort(MLinkedList a, int low, int high){
        if(high > low) {

            int p = a.partition(low, high);

            quickSort(a, low, p);
            quickSort(a, p + 1, high);
        }
        return a;
    }

    private int partition (int low, int high){
        Node pivot = first;

        for (int x = 0; x < low; x++) {
            pivot = pivot.getNextNode();
        }

        int i = low - 1;
        int j = high + 1;

        while (true){
            do {
                i++;
//                System.out.println(getNode(i).getElement()+":"+pivot.getElement());
            } while (getNode(i).getElement().compareTo(pivot.getElement()) < 0);

            do{
                j--;
//                System.out.println(getNode(i).getElement()+":"+pivot.getElement());
            } while (getNode(j).getElement().compareTo(pivot.getElement()) > 0);

            if ( i >= j)
                return j;

            //Swap
            Comparable a = get(i);
            Comparable b = get(j);
            set(i, b);
            set(j, a);
        }
    }

    /**
     * Returns the number of elements in this list.  If this list contains
     * more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return total;
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return total == 0;
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(java.lang.Object o) {
        Node currentNode = first;
        while (currentNode.getElement() != o){
            Node nextNode = currentNode.getNextNode();
            if(nextNode == null){
                return false;
            } else {
                currentNode = nextNode;
            }
        }
        return true;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator iterator() {
        return null;
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     * <p>
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must
     * allocate a new array even if this list is backed by an array).
     * The caller is thus free to modify the returned array.
     * <p>
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list in proper
     * sequence
     * @see Arrays#asList(java.lang.Object[])
     */
    @Override
    public java.lang.Object[] toArray() {
        java.lang.Object[] array = new java.lang.Object[total];
        Node currentNode = first;
        int count = 0;
        while (currentNode.containsNextNode()){
            array[count] = currentNode.getElement();
            currentNode = currentNode.getNextNode();
            count++;
        }
        //Final element
//        array[count-1] = currentNode.getElement();
        return array;
    }

    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     * <p>
     * <p>Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some
     * lists will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what elements may be added.
     *
     * @param o element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified element is null and this
     *                                       list does not permit null elements
     * @throws IllegalArgumentException      if some property of this element
     *                                       prevents it from being added to this list
     */
    @Override
    public boolean add(java.lang.Object o) {
        if(o == null){
            throw new NullPointerException();
        }

        if (first == null){
            first = new Node((Comparable) o, null);
            total++;
            return true;
        } else {
            Node currentNode = first;
            while (currentNode.getNextNode() != null){
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(new Node((Comparable)o, null));
            total++;
            return true;
        }
    }

    public void append(Node node){
        if(first == null){
            first = node;
        } else {
            Node current = first;
            while (current.containsNextNode()){
                current = current.getNextNode();
            }
            current.setNextNode(node);
        }

        //Placed here to avoid any errors cropping up
        total ++;
    }

    public void append(Object object){
        if(first == null){
            first = new Node(object);
        } else {
            Node current = first;
            while (current.containsNextNode()){
                current = current.getNextNode();
            }
            current.setNextNode(new Node(object));
        }

        //Placed here to avoid any errors cropping up
        total ++;
    }

    public void append(MLinkedList mLinkedList){
        if(first == null){
            first = mLinkedList.first;
        } else {
            Node current = first;
            while (current.containsNextNode()){
                current = current.getNextNode();
            }
            current.setNextNode(mLinkedList.first);
        }

        //Placed here to avoid any errors cropping up
        total += mLinkedList.size();
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present (optional operation).  If this list does not contain
     * the element, it is unchanged.  More formally, removes the element with
     * the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list changed
     * as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this list
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       list does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this list
     */
    @Override
    public boolean remove(java.lang.Object o) {
        Node currentNode = first;
        Node previous = null;
        while (currentNode != null){
            if (currentNode.getElement() == o){
                if(previous == null){
                    first.setNextNode(currentNode.getNextNode());
                } else {
                    previous.setNextNode(currentNode.getNextNode());
                }
                total--;
                return true;
            } else {
                previous = currentNode;
                currentNode = currentNode.getNextNode();
            }
        }
        return false;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this list does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     * @see #add(java.lang.Object)
     */
    @Override
    public boolean addAll(Collection c) {
        MLinkedList list = (MLinkedList) c;
        add(list.first);

        return true;
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list at the specified position (optional operation).  Shifts the
     * element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this list in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this list does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this list
     */
    @Override
    public void clear() {
        total = 0;
        first = null;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public Comparable get(int index) {
        if (index >= total){
            throw  new IndexOutOfBoundsException();
        } else if(first == null){
            return null;
        } else {
            Node node = first;
            for (int i = 0; i < index; i++) {
                node = node.getNextNode();
            }
            return node.getElement();
        }

    }

    public Node getNode(int index){
        if (index >= total){
            throw  new IndexOutOfBoundsException();
        } else if(first == null){
            return null;
        } else {
            Node node = first;
            for (int i = 0; i < index; i++) {
                node = node.getNextNode();
            }
            return node;
        }
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>set</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified element is null and
     *                                       this list does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified
     *                                       element prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public java.lang.Object set(int index, java.lang.Object element) {
        if (index >= total)
            throw new IndexOutOfBoundsException();

        if (element == null)
            throw new NullPointerException();

        java.lang.Object oldElement = null;
        Node currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        oldElement = currentNode.getElement();
        currentNode.setElement((Comparable)element);

        return oldElement;
    }

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified element is null and
     *                                       this list does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified
     *                                       element prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    @Override
    public void add(int index, java.lang.Object element) {
        if (1 > index || index > size()){
            throw new IndexOutOfBoundsException();
        } else if(element == null){
            throw new NullPointerException();
        }
        Node<Object> current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNextNode();
        }
        if(current.containsNextNode()){
            Node node = new Node<Object>((Object) element, current.getNextNode());
            current.setNextNode(node);
        } else {
            current.setNextNode(new Node<Object>((Object) element));
        }
        total++;
    }

    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public Comparable remove(int index) {
        if(index >= size() || 0 > index){
            throw new IndexOutOfBoundsException("Cannot get " + index);
        }

        Node previous = null;
        Node current = first;
        for (int i = 0; i < index; i++) {
            previous = current;
            current = current.getNextNode();
        }

        if(current == null){
            return null;
        } else {
            Comparable element = current.getElement();
            if(current.getNextNode() != null)
            previous.setNextNode(current.getNextNode());
            total--;
            return element;
        }

    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(java.lang.Object o) {
        Node currentNode = first;
        for (int i = 0; i < total; i++) {
            if(currentNode.getElement() == o){
                return i;
            } else {
                currentNode = currentNode.getNextNode();
            }

        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(java.lang.Object o) {
        int index = -1;
        Node currentNode = first;
        for (int i = 0; i < total; i++) {
            if(currentNode.getElement() == o){
                index = i;
            }
            currentNode = currentNode.getNextNode();
        }

        return index;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    @Override
    public ListIterator listIterator() {

        return new LLIterator(this);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    @Override
    public ListIterator listIterator(int index) {
        if(1 > index || index > size())
            throw new IndexOutOfBoundsException();

        LLIterator llIterator = new LLIterator(this);
        for (int i = 0; i < index; i++) {
            llIterator.next();
        }

        return llIterator;
    }

    /**
     * Returns a view of the portion of this list between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported
     * by this list.<p>
     * <p>
     * This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>{@code
     *      list.subList(from, to).clear();
     * }</pre>
     * Similar idioms may be constructed for <tt>indexOf</tt> and
     * <tt>lastIndexOf</tt>, and all of the algorithms in the
     * <tt>Collections</tt> class can be applied to a subList.<p>
     * <p>
     * The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *                                   (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *                                   fromIndex &gt; toIndex</tt>)
     */
    @Override
    public List subList(int fromIndex, int toIndex) {
        if(1 > fromIndex || toIndex > total){
            throw new IndexOutOfBoundsException();
        }
        MLinkedList<Object> linkedList = new MLinkedList();
        Node start = first;
        //Itterate to where we want to be
        for (int i = 0; i < fromIndex; i++) {
            start = start.getNextNode();
        }

        //Continue until the end
        for (int i = 0; i < toIndex; i++) {
            linkedList.add(start.getElement());
            start = start.getNextNode();
        }
        linkedList.first = start;

        return linkedList;
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all of its elements that are not contained in the
     * specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of this list
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this list contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(java.lang.Object)
     * @see #contains(java.lang.Object)
     */
    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection containing elements to be removed from this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of this list
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this list contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(java.lang.Object)
     * @see #contains(java.lang.Object)
     */
    @Override
    public boolean removeAll(Collection c) {
        if(!containsAll(c)){
            return false;
        } else {
            for (java.lang.Object o : c){
                if (!remove(o)){
                    throw new NullPointerException();
                }
            }
        }

        return true;
    }

    /**
     * Returns <tt>true</tt> if this list contains all of the elements of the
     * specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return <tt>true</tt> if this list contains all of the elements of the
     * specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this list does not permit null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(java.lang.Object)
     */
    @Override
    public boolean containsAll(Collection c) {
        for (java.lang.Object o : c){
            if(!c.contains(o)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns an array containing all of the elements in this list in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the list fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this list.
     * <p>
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array
     * immediately following the end of the list is set to <tt>null</tt>.
     * (This is useful in determining the length of the list <i>only</i> if
     * the caller knows that the list does not contain any null elements.)
     * <p>
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * <p>
     * <p>Suppose <tt>x</tt> is a list known to contain only strings.
     * The following code can be used to dump the list into a newly
     * allocated array of <tt>String</tt>:
     * <p>
     * <pre>{@code
     *     String[] y = x.toArray(new String[0]);
     * }</pre>
     * <p>
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public java.lang.Object[] toArray(java.lang.Object[] a) {
        return new java.lang.Object[0];
    }

    class Node<T extends Comparable> {
        private Node<T> nextNode;
        private T element;

        public Node(T element){
            this.element = element;
            this.nextNode = null;
        }

        public Node(T element, Node<T> nextNode){
            this.element = element;
            this.nextNode = nextNode;
        }


        public Node getNextNode() {
            return nextNode;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public void setNextNode(Node<T> nextNode) {
                this.nextNode = nextNode;
        }

        public boolean containsNextNode() {return nextNode != null;}
    }

    class LLIterator implements ListIterator{
        private final MLinkedList<Object> linkedList;
        private Node<Object> current;
        private int count = 0;

        LLIterator(MLinkedList<Object> linkedList){
            this.linkedList = linkedList;
            this.current = linkedList.first;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current.containsNextNode();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Comparable next() {
            if (current.containsNextNode()){
                Comparable element = current.getElement();
                current = current.getNextNode();
                return element;
            } else {
                throw new NoSuchElementException();
            }

        }

        /**
         * Returns {@code true} if this list iterator has more elements when
         * traversing the list in the reverse direction.  (In other words,
         * returns {@code true} if {@link #previous} would return an element
         * rather than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when
         * traversing the list in the reverse direction
         */
        @Override
        public boolean hasPrevious() {
            return false;
        }

        /**
         * Returns the previous element in the list and moves the cursor
         * position backwards.  This method may be called repeatedly to
         * iterate through the list backwards, or intermixed with calls to
         * {@link #next} to go back and forth.  (Note that alternating calls
         * to {@code next} and {@code previous} will return the same
         * element repeatedly.)
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if the iteration has no previous
         *                                element
         */
        @Override
        public java.lang.Object previous() {
            throw new NoSuchElementException();
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #next}. (Returns list size if the list
         * iterator is at the end of the list.)
         *
         * @return the index of the element that would be returned by a
         * subsequent call to {@code next}, or list size if the list
         * iterator is at the end of the list
         */
        @Override
        public int nextIndex() {
            return count+1;
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #previous}. (Returns -1 if the list
         * iterator is at the beginning of the list.)
         *
         * @return the index of the element that would be returned by a
         * subsequent call to {@code previous}, or -1 if the list
         * iterator is at the beginning of the list
         */
        @Override
        public int previousIndex() {
            return count-1;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Replaces the last element returned by {@link #next} or
         * {@link #previous} with the specified element (optional operation).
         * This call can be made only if neither {@link #remove} nor {@link
         * #add} have been called after the last call to {@code next} or
         * {@code previous}.
         *
         * @param o the element with which to replace the last element returned by
         *          {@code next} or {@code previous}
         * @throws UnsupportedOperationException if the {@code set} operation
         *                                       is not supported by this list iterator
         * @throws ClassCastException            if the class of the specified element
         *                                       prevents it from being added to this list
         * @throws IllegalArgumentException      if some aspect of the specified
         *                                       element prevents it from being added to this list
         * @throws IllegalStateException         if neither {@code next} nor
         *                                       {@code previous} have been called, or {@code remove} or
         *                                       {@code add} have been called after the last call to
         *                                       {@code next} or {@code previous}
         */
        @Override
        public void set(java.lang.Object o) {
            current.setElement((Object) o);
        }

        /**
         * Inserts the specified element into the list (optional operation).
         * The element is inserted immediately before the element that
         * would be returned by {@link #next}, if any, and after the element
         * that would be returned by {@link #previous}, if any.  (If the
         * list contains no elements, the new element becomes the sole element
         * on the list.)  The new element is inserted before the implicit
         * cursor: a subsequent call to {@code next} would be unaffected, and a
         * subsequent call to {@code previous} would return the new element.
         * (This call increases by one the value that would be returned by a
         * call to {@code nextIndex} or {@code previousIndex}.)
         *
         * @param o the element to insert
         * @throws UnsupportedOperationException if the {@code add} method is
         *                                       not supported by this list iterator
         * @throws ClassCastException            if the class of the specified element
         *                                       prevents it from being added to this list
         * @throws IllegalArgumentException      if some aspect of this element
         *                                       prevents it from being added to this list
         */
        @Override
        public void add(java.lang.Object o) {
            current.setNextNode(new Node<Object>((Object) o, current.getNextNode()));
        }
    }
}
