/*
 * Copyright (C) 2009 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.zlibrary.core.tree;

import java.util.*;

public abstract class ZLTree<T extends ZLTree> {
	private int mySize = 1;
	public final T Parent;
	public final int Level;
	private ArrayList<T> mySubTrees;

	protected ZLTree() {
		this(null);
	}

	protected ZLTree(T parent) {
		Parent = parent;
		if (parent != null) {
			Level = parent.Level + 1;
			parent.addSubTree(this);
		} else {
			Level = 0;
		}
	}

	public final int getSize() {
		return mySize;
	}

	public final boolean hasChildren() {
		return mySubTrees != null;
	}

	public final List<T> subTrees() {
		if (mySubTrees == null) {
			return Collections.emptyList();
		}
		return mySubTrees;
	}

	public final T getTree(int index) {
		if ((index < 0) || (index >= mySize)) {
			// TODO: throw exception?
			return null;
		}
		if (index == 0) {
			return (T)this;
		}
		--index;
		for (T subtree : mySubTrees) {
			if (subtree.mySize <= index) {
				index -= subtree.mySize;
			} else {
				return (T)subtree.getTree(index);
			}
		}
		throw new RuntimeException("That's impossible!!!");
	}

	private void addSubTree(T subtree) {
		if (mySubTrees == null) {
			mySubTrees = new ArrayList<T>();
		}
		final int subTreeSize = subtree.getSize();
		mySubTrees.add(subtree);
		for (ZLTree parent = this; parent != null; parent = parent.Parent) {
			parent.mySize += subTreeSize;
		}
	}

	public final void clear() {
		mySubTrees = null;
		mySize = 1;
	}
}