//
//  ContactUsViewController.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 01/07/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit

class ContactUsViewController: UIViewController,UITableViewDelegate,UITableViewDataSource {

    
    @IBOutlet weak var tableView: UITableView!
    
    
    var personData = [["Kiran Jain","CEO","+91 8376026716","Kiran.jpg","kjain3372@gmail.com"],["Sankalp Katiyar","CEO","+91 9582059505","Sankalp.jpg","sankalpkatiyar.com@gmail.com"],["Rahul Mehta","Project Executive","+91 8824633771","Rahul.jpg","rahulbhushanmehta@gmail.com"],["Ajay Agarwal","Project Executive","+91 8107920251","Ajay.jpg","ajayagarwal28598@gmail.com"],["Piyush Kansal","Project Executive","+91 8708641376","Piyush.jpg","piyush.logics@gmail.com"],["Rushil Gupta","Project Executive","+91 7973204443","Rushil.jpg","rushilgupta26051999@gmail.com"],["Sakshi Gupta","Project Executive","+91 9987241654","Sakshi.jpg","sakshigupta1771@gmail.com"],["Sahil Dahake","Project Executive","+91 9172264974","Sahil.jpg","sahildahake@gmail.com"],["Somendra Singh Jadon","Project Executive","+91 8349044800","Somendra.jpg","somendra07jadon@gmail.com"],["Naman Bhargava","Executive Head","+91 8130968478","Naman.jpg","namanbhargava18@gmail.com"],["Aditi Gupta","Executive Head","+91 8826093285","Aditi.jpg","aditigupta802@gmail.com"],["Sahil Vaish","Project Leader","+91 8860753261","Sahil2.jpg","svaish758@gmail.com"]]
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.delegate = self
        tableView.dataSource = self

        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personData.count
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! ContactUsCell
        cell.nameLabel.text = personData[indexPath.row][0]
        cell.designationLabel.text = personData[indexPath.row][1]
        cell.phoneNumberLabel.text = personData[indexPath.row][2]
        cell.emailLabel.text = personData[indexPath.row][4]
        cell.profileImageView.clipsToBounds = true
        cell.profileImageView.image = UIImage(named: "\(personData[indexPath.row][3])")
        cell.profileImageView.contentMode = .scaleAspectFill
        tableView.tableFooterView = UIView()
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 350.0
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: false)
    }

  
}
