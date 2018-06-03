package models

import java.time.LocalDate


case class Payment(
                    id: Long,
                    timestamp: LocalDate,
                    userId: String,
                    comment: String,
                    paymentType: PaymentType.Value,
                    amount: Double,
                    paypackId: String,
                    status: PaymentStatus.Value,
                    balance: Double
                  )
