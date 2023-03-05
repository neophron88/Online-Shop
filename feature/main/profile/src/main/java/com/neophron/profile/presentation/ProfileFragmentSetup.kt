package com.neophron.profile.presentation

import androidx.core.view.isVisible
import com.neophron.profile.R
import com.neophron.profile.databinding.ProfileFragmentBinding

fun ProfileFragment.setupUserFeatures(binding: ProfileFragmentBinding) = binding.apply {

    tradeStoreItem.apply {
        name.text = getString(R.string.trade_store)
        itemIcon.setImageResource(R.drawable.ic_card)
        actionIcon.isVisible = true
    }

    paymentMethodItem.apply {
        name.text = getString(R.string.payment_method)
        itemIcon.setImageResource(R.drawable.ic_card)
        actionIcon.isVisible = true
    }

    balanceItemValue.apply {
        name.text = getString(R.string.balance)
        itemIcon.setImageResource(R.drawable.ic_card)
        value.isVisible = true
        value.text = getString(R.string.test_dollar_text)
    }

    tradeHistoryItem.apply {
        name.text = getString(R.string.trade_history)
        itemIcon.setImageResource(R.drawable.ic_card)
        actionIcon.isVisible = true
    }

    restorePurchaseItem.apply {
        name.text = getString(R.string.restore_purchase)
        itemIcon.setImageResource(R.drawable.ic_restore)
        actionIcon.isVisible = true
    }

    helpItem.apply {
        name.text = getString(R.string.help)
        itemIcon.setImageResource(R.drawable.ic_question)
    }

    logOutItem.apply {
        name.text = getString(R.string.log_out)
        itemIcon.setImageResource(R.drawable.ic_log_out)
    }

}
