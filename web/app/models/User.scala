package models

case class User (
  username: String,
  subscriptions: Seq[Subscription]
)