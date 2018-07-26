//
//  ProposalViewController.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 15/07/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class ProposalViewController: UIViewController,IndicatorInfoProvider {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "Proposals")
    }

}
