//
//  ResourcesViewController.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 15/07/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class ResourcesViewController: ButtonBarPagerTabStripViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        
        tabSettings()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        
        let storyBoard = UIStoryboard(name: "Main", bundle: nil)
        
        let chil1 = storyBoard.instantiateViewController(withIdentifier: "overview")
        let chil2 = storyBoard.instantiateViewController(withIdentifier: "proposals")
        let chil3 = storyBoard.instantiateViewController(withIdentifier: "tasks")
        let chil4 = storyBoard.instantiateViewController(withIdentifier: "chat")
        
        
        return [chil1,chil2,chil3,chil4]
    }
    
    
    func tabSettings(){
        
        //all the tab style settings
        self.settings.style.selectedBarHeight = 30.0
        self.settings.style.buttonBarHeight = 10.0
        self.settings.style.buttonBarBackgroundColor = UIColor.black
        self.settings.style.buttonBarItemTitleColor = UIColor.white
        self.settings.style.selectedBarBackgroundColor = UIColor.white
        self.settings.style.buttonBarItemsShouldFillAvailableWidth = true
        self.settings.style.buttonBarLeftContentInset = 0
        self.settings.style.buttonBarRightContentInset = 0
        self.settings.style.buttonBarMinimumLineSpacing = 0.0
         self.settings.style.buttonBarItemBackgroundColor = UIColor(red: 18.0/255.0, green: 18.0/255.0, blue: 18.0/255.0, alpha: 1.0)
        
        changeCurrentIndexProgressive = { (oldCell: ButtonBarViewCell?, newCell: ButtonBarViewCell?, progressPercentage: CGFloat, changeCurrentIndex: Bool, animated: Bool) -> Void in
            guard changeCurrentIndex == true else { return }
            
            oldCell?.label.textColor = UIColor(white: 1, alpha: 0.5)
            newCell?.label.textColor = .white
            
        }
        
    }


}
