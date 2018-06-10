package edu.washington.ronney.idle

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.TextView


class shop : AppCompatActivity() {

    var money = 0

    var boughtApple = false
    var boughtTree = false
    var boughtFarm = false
    var applePerSecond = 0
    var applePerSecondCost = 50

    var treeEnabled = false
    var treeCost = 1000
    var treePerSecond = 0
    var treePerSecondCost = 50000


    var farmEnabled = false
    var farmCost = 10000
    var farmPerSecond = 0
    var farmPerSecondCost = 500000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        money = intent.extras.getInt("money")
        boughtApple = intent.extras.getBoolean("boughtApple")
        boughtTree = intent.extras.getBoolean("boughtTree")
        boughtFarm = intent.extras.getBoolean("boughtFarm")
        applePerSecond = intent.extras.getInt("aps")
        applePerSecondCost = intent.extras.getInt("apsCost")
        treeEnabled = intent.extras.getBoolean("treeEnabled")
        farmEnabled = intent.extras.getBoolean("farmEnabled")
        treePerSecond = intent.extras.getInt("tps")
        farmPerSecond = intent.extras.getInt("fps")

        val apples = findViewById<TextView>(R.id.apples)
        val appleButton = findViewById<Button>(R.id.appleButton)
        val treeButton = findViewById<Button>(R.id.treeButton)
        val farmButton = findViewById<Button>(R.id.farmButton)
        val back = findViewById<Button>(R.id.shopBack)

        val applePS = findViewById<TextView>(R.id.buyAPS)
        val apsCost = findViewById<TextView>(R.id.apsCost)
        val treePS = findViewById<TextView>(R.id.buyATPS)
        val atpsCost = findViewById<TextView>(R.id.atpsCost)
        val farmPS = findViewById<TextView>(R.id.buyAFPS)
        val afpsCost = findViewById<TextView>(R.id.afpsCost)

        val buyTree = findViewById<Button>(R.id.buyTree)
        val buyFarm = findViewById<Button>(R.id.buyFarm)

        if (boughtApple) {
            applePS.setText(Integer.toString(applePerSecond) + " Apples/sec")
        }
        apsCost.setText("$" + applePerSecondCost)

        if (boughtTree) {
            treePS.setText(Integer.toString(treePerSecond) + " Apples/sec")
        }
        atpsCost.setText("$" + treePerSecondCost)

        if (boughtFarm) {
            farmPS.setText(Integer.toString(farmPerSecond) + " Apples/sec")
        }
        afpsCost.setText("$" + farmPerSecondCost)

        apples.setText("Apples: " + money)



        if (money >= applePerSecondCost) {
            appleButton.setEnabled(true)
        }



        if (money >= treePerSecondCost) {
            treeButton.setEnabled(true)
        }
        if (treeEnabled) {
            treeButton.setVisibility(View.VISIBLE)
            atpsCost.setVisibility(View.VISIBLE)
            findViewById<TextView>(R.id.atCost).setVisibility(View.INVISIBLE)
        }

        if (money >= farmPerSecondCost) {
            farmButton.setEnabled(true)
        }
        if (farmEnabled) {
            farmButton.setVisibility(View.VISIBLE)
            afpsCost.setVisibility(View.VISIBLE)
            findViewById<TextView>(R.id.afCost).setVisibility(View.INVISIBLE)
        }


        if ((money >= treeCost)) {
            buyTree.setEnabled(true)
        }
        if ((money >= farmCost)) {
            buyFarm.setEnabled(true)
        }

        appleButton.setOnClickListener() {
            money = money - applePerSecondCost
            applePerSecondCost *= 2
            if (money < applePerSecondCost) {
                appleButton.setEnabled(false)
            }

            apples.setText("Apples: " + money)
            applePerSecond += 1
            boughtApple = true

            applePS.setText(Integer.toString(applePerSecond) + " Apples/sec")
            apsCost.setText("$" + applePerSecondCost)
        }

        treeButton.setOnClickListener() {
            money = money - treePerSecondCost
            treePerSecondCost *= 2
            if (money < treePerSecondCost) {
                treeButton.setEnabled(false)
            }

            apples.setText("Apples: " + money)
            treePerSecond += 100
            boughtTree = true

            treePS.setText(Integer.toString(treePerSecond) + " Apples/sec")
            atpsCost.setText("$" + treePerSecondCost)
        }

        farmButton.setOnClickListener() {
            money = money - farmPerSecondCost
            farmPerSecondCost *= 2
            if (money < farmPerSecondCost) {
                farmButton.setEnabled(false)
            }

            apples.setText("Apples: " + money)
            farmPerSecond += 1000
            boughtFarm = true

            farmPS.setText(Integer.toString(farmPerSecond) + " Apples/sec")
            afpsCost.setText("$" + farmPerSecondCost)
        }

        buyTree.setOnClickListener() {
            money -= treeCost
            apples.setText("Apples: " + money)
            treeEnabled = true
            buyTree.setVisibility(View.INVISIBLE)
            treeButton.setVisibility(View.VISIBLE)
            findViewById<TextView>(R.id.atCost).setVisibility(View.INVISIBLE)
            findViewById<TextView>(R.id.atpsCost).setVisibility(View.VISIBLE)
        }

        buyFarm.setOnClickListener() {
            money -= farmCost
            apples.setText("Apples: " + money)
            farmEnabled = true
            buyFarm.setVisibility(View.INVISIBLE)
            farmButton.setVisibility(View.VISIBLE)
            findViewById<TextView>(R.id.afCost).setVisibility(View.INVISIBLE)
            findViewById<TextView>(R.id.afpsCost).setVisibility(View.VISIBLE)
        }

        back.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("money", money)
            intent.putExtra("boughtApple", boughtApple)
            intent.putExtra("boughtTree", boughtTree)
            intent.putExtra("boughtFarm", boughtFarm)
            intent.putExtra("aps" , applePerSecond)
            intent.putExtra("tps" , treePerSecond)
            intent.putExtra("fps" , farmPerSecond)
            intent.putExtra("apsCost", applePerSecondCost)
            intent.putExtra("atpsCost", treePerSecondCost)
            intent.putExtra("afpsCost", farmPerSecondCost)
            intent.putExtra("treeEnabled", treeEnabled)
            intent.putExtra("farmEnabled", farmEnabled)
            setResult(RESULT_OK, intent)
            finish()
        }

        if (treeEnabled) {
            buyTree.setVisibility(View.INVISIBLE)
        }

        if (farmEnabled) {
            buyFarm.setVisibility(View.INVISIBLE)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("money", money)
        intent.putExtra("boughtApple", boughtApple)
        intent.putExtra("boughtTree", boughtTree)
        intent.putExtra("boughtFarm", boughtFarm)
        intent.putExtra("aps" , applePerSecond)
        intent.putExtra("tps" , treePerSecond)
        intent.putExtra("fps" , farmPerSecond)
        intent.putExtra("apsCost", applePerSecondCost)
        intent.putExtra("atpsCost", treePerSecondCost)
        intent.putExtra("afpsCost", farmPerSecondCost)
        intent.putExtra("treeEnabled", treeEnabled)
        intent.putExtra("farmEnabled", farmEnabled)
        setResult(RESULT_OK, intent)
        finish()
    }
}
