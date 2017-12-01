import Permission._

package object Types {
  type Data = Map[String, Map[String, Map[String, Permission]]]
}