package TP1

object exo2 extends App {
 
  abstract class IntTree;
  case object EmptyIntTree extends IntTree;
  case class NodeInt(elem: Int, left: IntTree, right : IntTree) extends IntTree;
  
  def contains(t: IntTree, v: Int): Boolean = t match {
    case EmptyIntTree => return false
    case NodeInt(elem,left,right) => if(elem == v) return true
                                     return contains(left,v) || contains(right,v)
  }
  
  def size(t: IntTree):Int = t match {
    case EmptyIntTree => return 0
    case NodeInt(_,left,right) => return 1 + size(left) + size(right)
  }
  
  def insert(t: IntTree, v: Int): IntTree = t match {
    case EmptyIntTree => return NodeInt(v,EmptyIntTree,EmptyIntTree)
    case NodeInt(e,left,right) if(size(left) > size(right)) => NodeInt(e,left, insert(right,v))
    case NodeInt(e,left,right) => NodeInt(e,left, insert(right,v)) 
  }
  
  var tree: IntTree = EmptyIntTree
  for(v <- 1 to 10) {
    tree = insert(tree,v)
  }
  assert(size(tree) == 10)
  for(v <- 1 to 10) {
    assert(contains(tree,v))
  }
  for(v <- 11 to 20){
    assert(!contains(tree,v))
  }
  println("Ok")
  
  
  abstract class Tree[+A];
  case object EmptyTree extends Tree;
  case class Node[A](elem : A, left: Tree[A], right: Tree[A]) extends Tree; 
  
  def contains[A](t: Tree[A], v: A): Boolean = t match {
    case EmptyTree => return false
    case Node(elem,left,right) => if(elem == v) return true
                                     return contains(left,v) || contains(right,v)
  }
  
  def size[A](t: Tree[A]):Int = t match {
    case EmptyTree => return 0
    case Node(_,left,right) => return 1 + size(left) + size(right)
  }
  
  def insert[A](t: Tree[A], v: A): Tree[A] = t match {
    case EmptyTree => return Node[A](v,EmptyTree,EmptyTree)
    case Node(e,left,right) if(size(left) > size(right)) => Node(e,left, insert(right,v))
    case Node(e,left,right) => Node(e,left, insert(right,v)) 
  }
  
  var treeGen: Tree[Int] = EmptyTree
  for(v <- 1 to 10) {
    treeGen = insert(treeGen,v)
  }
  assert(size(treeGen) == 10)
  for(v <- 1 to 10) {
    assert(contains(treeGen,v))
  }
  for(v <- 11 to 20){
    assert(!contains(treeGen,v))
  }
  println("Ok")
  
}