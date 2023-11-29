package com.example.aneon_test_case.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aneon_test_case.R
import com.example.aneon_test_case.data.models.Payment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentsAdapter : RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {

    private var payments: List<Payment> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = payments[position]
        holder.bind(payment)
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    fun setPaymentsList(payments: List<Payment>) {
        this.payments = payments
        notifyDataSetChanged()
    }

    class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewId: TextView = itemView.findViewById(R.id.textViewId)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        private val textViewCreated: TextView = itemView.findViewById(R.id.textViewCreated)

        fun bind(payment: Payment) {
            textViewId.text = "ID: ${payment.id}"
            textViewTitle.text = "Title: ${payment.title}"
            textViewAmount.text = "Amount: ${payment.amount ?: "N/A"}"
            textViewCreated.text = "Created: ${convertTimestampToDateString(payment.created)}"
        }

        private fun convertTimestampToDateString(timestamp: Long): String {
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = Date(timestamp * 1000)
            return sdf.format(date)
        }
    }
}

