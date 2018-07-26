//
//  ContactUsCell.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 01/07/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit

class ContactUsCell: UITableViewCell {

    
    @IBOutlet weak var phoneNumberLabel: UILabel!
    @IBOutlet weak var designationLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var backView: UIView!
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var emailLabel: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
