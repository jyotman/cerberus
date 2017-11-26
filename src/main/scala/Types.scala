import scala.collection.immutable.HashMap

package object Types {
  type Data = HashMap[String, HashMap[String, HashMap[String, Permission]]]
}