//
//  ViewController.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 23/06/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
  
    @IBOutlet weak var guestButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guestButton.layer.cornerRadius = guestButton.frame.height/2
        
        loginButton.layer.cornerRadius = loginButton.frame.height/2
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

