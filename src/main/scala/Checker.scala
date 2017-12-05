object Checker {

  def check(attributesToCheck: List[String], attributes: List[String]): Boolean = attributesToCheck match {
    case head :: tail => check(head, attributes) && check(tail, attributes)
    case Nil => true
  }

  def check(attributeToCheck: String, attributes: List[String]): Boolean =
    (attributes.contains(attributeToCheck) || attributes.contains("*")) &&
      !attributes.contains(negate(attributeToCheck))

  private def negate(attribute: String): String = "!" + attribute
}