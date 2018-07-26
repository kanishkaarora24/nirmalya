//
//  ViewControllerOne.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 28/06/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit
import Firebase

class productViewController: UIViewController,UITableViewDelegate,UITableViewDataSource {
    
    @IBOutlet weak var tableView: UITableView!
    
    let productHeaderData = [["Nirmalya sustains at the intersection of innovation and interaction. Our solutions vary from Zero waste customised models for societies to triple-decker ethnic home composters for decentralised management of waste."],["Ruchitra: Ruchitra is the wire mesh installed in garden area to specifically target the horticulture waste. With capacity over a tonne, it can furnish upto 300 Kg of compost in less than three months.\n\nOur Sailent Features include:\n1. Improved Aeration\n2. Easy Compost Harving\n3. Additives to enhance the quality of compost and reduce the duration of composting\n4. No foul Smell.","Sugriha: Sugriha comes into picture as the successor of our conventional bins; a compact machine to be kept in your kitchens, lobby, veranda or gardens. With customisable aesthetics and manual operation,\n\nFeatures of Sugriha are:-\n1.Sugriha is hygienic\n2.Odour free and imparts a traditional ethnic look to your house.\n3.Hygenic and Odourless\n4.Faster Composting through Aerobic Methods.\n5.Minimal user effort\n6.Provides Continuous compost\n7.Ensures easy waste segregation.","Pitara: Pitara is an overground pit that comes in two sizes and targets to manage the wet waste of the society at community level.\n\nSalient Features include-\n1.A bricked overground structure with suitable gaps to ensure aeration\n2.Dimensions so chosen to ensure easy usage\n3.A cross-network of bamboo at the base to ensure proper leachate drainage\n4.Shed covering to safeguard it from the rains and excessive sunlight that may cause loss of moisture."],["Nirmalya offers it’s Jagruk Model to Schools wherein we deal with the horticulture waste of the school with special emphasis on the sensitisaton of the young generation on the serious issue of waste. Our approach has the following facets:Sustainable management of horticulture waste through mesh composting.Awareness of staff and students through our euphoric and interactive sessions, which sensitizes students about the magnitude of the problem and bring them to methods to efficiently deal with waste. Availability of organic manure for plants in school at practically no cost from its own composting hub."]]
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.delegate = self
        tableView.dataSource = self
        tableView.estimatedRowHeight = 100.0
        tableView.rowHeight = UITableViewAutomaticDimension
        
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func logoutButtonPressed(_ sender: Any) {
        
        try! Auth.auth().signOut()
        
    }
    @IBAction func tabOption(_ sender: Any) {
        let secondVC = ImpactViewController()
        secondVC.tabBarItem.title = "3rd tab"
        secondVC.tabBarItem.image = #imageLiteral(resourceName: "man")
        tabBarController?.viewControllers?.insert(secondVC, at: 2)  
    }
    
    //setting up the headers in the tableView.
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        
        switch section {
        case 0:
            return "What we offer."
        case 1:
            return "Our Products."
        case 2:
            return "KALP Model for Socities."
        default:
            return ""
        }
        
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return productHeaderData[section].count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! ProductCell
        
        cell.descriptionLabel.text = productHeaderData[indexPath.section][indexPath.row]
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: false)
    }
    
    
    
}
