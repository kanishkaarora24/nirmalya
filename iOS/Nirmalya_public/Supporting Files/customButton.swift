//
//  customButton.swift
//  Matchball Internship
//
//  Created by Ayush Verma on 26/05/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit

@IBDesignable
class customButton: UIButton {
    
    @IBInspectable var cornerRadius:CGFloat = 0{
        didSet{
            self.layer.cornerRadius = cornerRadius
        }
    }
    
    @IBInspectable var borderWidth:CGFloat = 0{
        didSet{
            self.layer.borderWidth = borderWidth
        }
    }
    
    @IBInspectable var borderColor:UIColor = UIColor.white{
        didSet{
            self.layer.borderColor = borderColor.cgColor
        }
    }
    
   
}
