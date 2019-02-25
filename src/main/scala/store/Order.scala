package store

case class OrderItem(item: Item, count: Int)

case class Order(orderItems: Seq[OrderItem])

object Order {
  def getOrderTotalPrice(order: Order): Double = {
    order.orderItems.foldLeft(0F) { case (acc, orderItem) => acc + orderItem.item.price * orderItem.count }
  }
}