/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2010, by Barak Naveh and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/* -------------------------
 * AbstractPathElementList.java
 * -------------------------
 * (C) Copyright 2007-2010, by France Telecom
 *
 * Original Author:  Guillaume Boulmier and Contributors.
 * Contributor(s):   John V. Sichi
 *
 * $Id$
 *
 * Changes
 * -------
 * 05-Jun-2007 : Initial revision (GB);
 * 05-Jul-2007 : Added support for generics (JVS);
 * 06-Dec-2010 : Bugfixes (GB);
 *
 */
package org.jgrapht.alg;

import java.util.*;

import org.jgrapht.*;


/**
 * List of paths <code>AbstractPathElement</code> with same target vertex.
 *
 * @author Guillaume Boulmier
 * @since July 5, 2007
 */
abstract class AbstractPathElementList<V,
    E, T extends AbstractPathElement<V, E>>
    extends AbstractList<T>
{
    //~ Instance fields --------------------------------------------------------

    protected Graph<V, E> graph;

    /**
     * Max number of stored paths.
     */
    protected int maxSize;

    /**
     * Stored paths, list of <code>AbstractPathElement</code>.
     */
    protected ArrayList<T> pathElements = new ArrayList<T>();

    /**
     * Target vertex of the paths.
     */
    protected V vertex;
    
    /**
     * Declare String exception for MAXSIZE NEGATIVE OR ZERO EXCEPTION
     */
    public static final String MAXSIZE_NEGATIVE_OR_ZERO_EXCEPTION_MESSAGE = "maxSize is negative or 0";


    //~ Constructors -----------------------------------------------------------

    /**
     * Creates paths obtained by concatenating the specified edge to the
     * specified paths.
     *
     * @param maxSize maximum number of paths the list is able to store.
     * @param elementList paths, list of <code>AbstractPathElement</code>.
     * @param edge edge reaching the end vertex of the created paths.
     *
     * @throws NullPointerException if the specified prevPathElementList or edge
     * is <code>null</code>.
     * @throws IllegalArgumentException if <code>maxSize</code> is negative or
     * 0.
     */
    protected AbstractPathElementList(
        Graph<V, E> graph,
        int maxSize,
        AbstractPathElementList<V, E, T> elementList,
        E edge)
    {
        if (maxSize <= 0) {
            throw new IllegalArgumentException(MAXSIZE_NEGATIVE_OR_ZERO_EXCEPTION_MESSAGE);
        }
        if (elementList == null) {
            throw new NullPointerException("elementList is null");
        }
        if (edge == null) {
            throw new NullPointerException("edge is null");
        }

        this.graph = graph;
        this.maxSize = maxSize;
        this.vertex =
            Graphs.getOppositeVertex(graph, edge, elementList.getVertex());
    }

    /**
     * Creates a list with an empty path. The list size is 1.
     *
     * @param maxSize maximum number of paths the list is able to store.
     *
     * @throws NullPointerException if the specified path-element is <code>
     * null</code>.
     * @throws IllegalArgumentException if <code>maxSize</code> is negative or
     * 0.
     * @throws IllegalArgumentException if <code>pathElement</code> is not
     * empty.
     */
    protected AbstractPathElementList(
        Graph<V, E> graph,
        int maxSize,
        T pathElement)
    {
        if (maxSize <= 0) {
            throw new IllegalArgumentException(MAXSIZE_NEGATIVE_OR_ZERO_EXCEPTION_MESSAGE);
        }
        if (pathElement == null) {
            throw new NullPointerException("pathElement is null");
        }
        if (pathElement.getPrevEdge() != null) {
            throw new IllegalArgumentException("path must be empty");
        }

        this.graph = graph;
        this.maxSize = maxSize;
        this.vertex = pathElement.getVertex();

        this.pathElements.add(pathElement);
    }

    /**
     * Creates an empty list. The list size is 0.
     *
     * @param maxSize maximum number of paths the list is able to store.
     *
     * @throws IllegalArgumentException if <code>maxSize</code> is negative or
     * 0.
     */
    protected AbstractPathElementList(Graph<V, E> graph, int maxSize, V vertex)
    {
        if (maxSize <= 0) {
            throw new IllegalArgumentException(MAXSIZE_NEGATIVE_OR_ZERO_EXCEPTION_MESSAGE);
        }

        this.graph = graph;
        this.maxSize = maxSize;
        this.vertex = vertex;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns path <code>AbstractPathElement</code> stored at the specified
     * index.
     */
    public T get(int index)
    {
        return this.pathElements.get(index);
    }

    /**
     * Returns target vertex.
     */
    public V getVertex()
    {
        return this.vertex;
    }

    /**
     * Returns the number of paths stored in the list.
     */
    public int size()
    {
        return this.pathElements.size();
    }
}

// End AbstractPathElementList.java
