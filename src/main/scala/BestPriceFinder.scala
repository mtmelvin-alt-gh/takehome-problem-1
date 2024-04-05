import scala.collection.mutable.ListBuffer

case class BestPriceFinder() {
  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]): Seq[BestGroupPrice] = {
    val joined = for {
      rate <- rates
      price <- prices if rate.rateCode == price.rateCode
    } yield (rate, price)

    val sorted = joined.sortBy(r => (r._2.price, r._1.rateGroup))
    val cabinPriceRates = sorted.map { case (rate, price) => CabinPriceRate(rate, price) }

    val rateGroups = rates.distinctBy(r => r.rateGroup)
    val cabinCodes = prices.distinctBy(p => p.cabinCode)

    val results = new ListBuffer[BestGroupPrice]

    for (cabinCode <- cabinCodes) {
      for (rateGroup <- rateGroups) {
        val maybeLowest = cabinPriceRates.find(
          p => p.rate.rateGroup == rateGroup.rateGroup && p.price.cabinCode == cabinCode.cabinCode)

        val lowest = maybeLowest.get

        results += BestGroupPrice(lowest.price.cabinCode, lowest.rate.rateCode, lowest.price.price, lowest.rate.rateGroup)

      }
    }

    Seq.from(results)
  }
}

