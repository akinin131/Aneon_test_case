package com.example.aneon_test_case.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aneon_test_case.R
import com.example.aneon_test_case.data.models.Payment
import com.example.aneon_test_case.databinding.ItemPaymentBinding
import com.example.aneon_test_case.utils.convertTimestampToDateString

class PaymentsAdapter(private val context: Context) :
    RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {

    private var payments: List<Payment> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = ItemPaymentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = payments[position]
        holder.bind(payment)
    }

    override fun getItemCount(): Int = payments.size

    fun setPaymentsList(newPayments: List<Payment>) {
        val diffResult = DiffUtil.calculateDiff(
            PaymentsDiffCallback(payments, newPayments)
        )
        payments = newPayments
        diffResult.dispatchUpdatesTo(this)
    }

    class PaymentViewHolder(private val binding: ItemPaymentBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(payment: Payment) {
            binding.textViewId.text = context.getString(R.string.payment_id, payment.id)
            binding.textViewTitle.text = context.getString(R.string.payment_title, payment.title)
            binding.textViewAmount.text = context.getString(
                R.string.payment_amount,
                payment.amount ?: context.getString(R.string.not_available)
            )
            binding.textViewCreated.text = context.getString(
                R.string.payment_created,
                payment.created.convertTimestampToDateString()
            )

        }
    }

    private class PaymentsDiffCallback(
        private val oldList: List<Payment>,
        private val newList: List<Payment>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}





