import scala.collection.mutable.ListBuffer

case class BestPriceFinder() {
  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]): Seq[BestGroupPrice] = {
    // Associate all the rates with prices for easier usage later
    val joined = for {
      rate <- rates
      price <- prices if rate.rateCode == price.rateCode
    } yield (rate, price)

    // Sort them by price and rate group so it's easier to quickly find the lowest price per rate group
    val sorted = joined.sortBy(r => (r._2.price, r._1.rateGroup))

    // And now put that into a Seq[CabinPriceRate] so we don't have to fool with tuples (for readability)
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

