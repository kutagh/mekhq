/*
 * NewRecruitDialog.java
 *
 * Created on July 16, 2009, 5:30 PM
 */

package mekhq.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import megamek.common.util.DirectoryItems;
import megamek.common.util.EncodeControl;
import mekhq.campaign.Campaign;
import mekhq.campaign.personnel.Person;
import mekhq.campaign.personnel.Rank;
import mekhq.campaign.personnel.Ranks;
import mekhq.gui.CampaignGUI;
import mekhq.gui.view.PersonViewPanel;

/**
 *
 * @author  Jay Lawson <jaylawson39 at yahoo.com>
 */
public class NewRecruitDialog extends javax.swing.JDialog {

    /**
	 * This dialog is used to both hire new pilots and to edit existing ones
	 * 
	 */
	private static final long serialVersionUID = -6265589976779860566L;
	private Person person;
    private Frame frame;
    private boolean newHire;
    private DirectoryItems portraits;
    
    private Campaign campaign;
    
    private CampaignGUI hqView;
    
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> choiceRanks;
    private javax.swing.JButton btnRandomName;
    private javax.swing.JButton btnRandomPortrait;
    private javax.swing.JButton btnChoosePortrait;
    private javax.swing.JButton btnEditPerson;
    private javax.swing.JButton btnRegenerate;

    private javax.swing.JPanel panBottomButtons;
    private javax.swing.JPanel panSidebar;
    private JScrollPane scrollView;

    /** Creates new form CustomizePilotDialog */
    public NewRecruitDialog(java.awt.Frame parent, boolean modal, Person person, Campaign campaign, CampaignGUI view, DirectoryItems portraits) {
        super(parent, modal);
        this.campaign = campaign;
        this.portraits = portraits;
        this.hqView = view;
        this.frame = parent;
        this.person = person;
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    private void refreshView() {
    	scrollView.setViewportView(new PersonViewPanel(person, campaign, portraits));
		//This odd code is to make sure that the scrollbar stays at the top
		//I cant just call it here, because it ends up getting reset somewhere later
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				scrollView.getVerticalScrollBar().setValue(0);
			}
		});
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        
        scrollView = new JScrollPane();
        panBottomButtons = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        panSidebar = new javax.swing.JPanel();
        btnRandomName = new javax.swing.JButton();
        btnRandomPortrait = new javax.swing.JButton();
        btnChoosePortrait = new javax.swing.JButton();
        btnEditPerson = new javax.swing.JButton();
        btnRegenerate = new javax.swing.JButton();
        choiceRanks = new javax.swing.JComboBox<String>();

        ResourceBundle resourceMap = ResourceBundle.getBundle("mekhq.resources.NewRecruitDialog", new EncodeControl()); //$NON-NLS-1$
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setTitle(resourceMap.getString("Form.title")); // NOI18N
        if(newHire) {
            setTitle(resourceMap.getString("Form.title.new")); // NOI18N
        }
        setName("Form"); // NOI18N
        getContentPane().setLayout(new java.awt.BorderLayout());

        panSidebar.setName("panButtons"); // NOI18N
        panSidebar.setLayout(new java.awt.GridLayout(6,1));
        
        choiceRanks.setName("choiceRanks"); // NOI18N
        refreshRanksCombo();
        choiceRanks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	changeRank();
            }
        });
        panSidebar.add(choiceRanks);
        
        btnRandomName.setText(resourceMap.getString("btnRandomName.text")); // NOI18N
        btnRandomName.setName("btnRandomName"); // NOI18N
        btnRandomName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	randomName();
            }
        });
        panSidebar.add(btnRandomName);
  
        btnRandomPortrait.setText(resourceMap.getString("btnRandomPortrait.text")); // NOI18N
        btnRandomPortrait.setName("btnRandomPortrait"); // NOI18N
        btnRandomPortrait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	randomPortrait();
            }
        });
        panSidebar.add(btnRandomPortrait);
      
        btnChoosePortrait.setText(resourceMap.getString("btnChoosePortrait.text")); // NOI18N
        btnChoosePortrait.setName("btnChoosePortrait"); // NOI18N
        btnChoosePortrait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	choosePortrait();
            }
        });
        panSidebar.add(btnChoosePortrait);
      
        btnEditPerson.setText(resourceMap.getString("btnEditPerson.text")); // NOI18N
        btnEditPerson.setName("btnEditPerson"); // NOI18N
        btnEditPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	editPerson();
            }
        });
        btnEditPerson.setEnabled(campaign.isGM());
        panSidebar.add(btnEditPerson);
       
        btnRegenerate.setText(resourceMap.getString("btnRegenerate.text")); // NOI18N
        btnRegenerate.setName("btnRegenerate"); // NOI18N
        btnRegenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	regenerate();
            }
        });
        btnRegenerate.setEnabled(campaign.isGM());
        panSidebar.add(btnRegenerate);
 
        panBottomButtons.setName("panButtons"); // NOI18N
        panBottomButtons.setLayout(new java.awt.GridBagLayout());
        btnOk.setText(resourceMap.getString("btnHire.text")); // NOI18N
        btnOk.setName("btnOk"); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hire();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;

        panBottomButtons.add(btnOk, gridBagConstraints);
        gridBagConstraints.gridx++;

        btnClose.setText(resourceMap.getString("btnClose.text")); // NOI18N
        btnClose.setName("btnClose"); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setVisible(false);
            }
        });
        panBottomButtons.add(btnClose, gridBagConstraints);

        scrollView.setMinimumSize(new java.awt.Dimension(450, 150));
        scrollView.setPreferredSize(new java.awt.Dimension(450, 150));
        scrollView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollView.setViewportView(null);
        refreshView();
        
        getContentPane().add(panSidebar, BorderLayout.LINE_START);
        getContentPane().add(scrollView, BorderLayout.CENTER);
        getContentPane().add(panBottomButtons, BorderLayout.PAGE_END);

        pack();
    }

    private void hire() {
    	if(campaign.recruitPerson(person)) {
        	person = campaign.newPerson(person.getPrimaryRole());
        	refreshRanksCombo();
        	campaign.changeRank(person, campaign.getRanks().getRankNumericFromNameAndProfession(person.getProfession(), (String)choiceRanks.getSelectedItem()), false);
    	}
        refreshView();
        refreshHqView();
    }

    private void refreshHqView() {
        hqView.refreshPersonnelList();
        hqView.refreshPatientList();
        hqView.refreshTechsList();
        hqView.refreshDoctorsList();
        hqView.refreshReport();
        hqView.refreshFinancialTransactions();
        hqView.refreshOverview();
    }

    private void randomName() {
    	person.setName(campaign.getRNG().generate(person.getGender() == Person.G_FEMALE));
    	refreshView();
	}
    
    private void randomPortrait() {
    	campaign.assignRandomPortraitFor(person);
    	refreshView();
    }
    
    private void choosePortrait() {
    	ImageChoiceDialog pcd = new ImageChoiceDialog(frame, true,
				person.getPortraitCategory(),
				person.getPortraitFileName(), portraits);
		pcd.setVisible(true);
		person.setPortraitCategory(pcd.getCategory());
		person.setPortraitFileName(pcd.getFileName());
		refreshView();
    }
    
    private void editPerson() {
    	int gender = person.getGender();
    	CustomizePersonDialog npd = new CustomizePersonDialog(frame, true, 
				person, 
				campaign);
		npd.setVisible(true);
		if(gender != person.getGender()) {
			randomPortrait();
		}
		refreshRanksCombo();
    	refreshView();
    }
    
    private void regenerate() {
    	person = campaign.newPerson(person.getPrimaryRole());
    	refreshRanksCombo();
        refreshView();
    }
    
    private void changeRank() {
    	campaign.changeRank(person, campaign.getRanks().getRankNumericFromNameAndProfession(person.getProfession(), (String)choiceRanks.getSelectedItem()), false);
    	refreshView();
    }
   
    private void refreshRanksCombo() {
    	DefaultComboBoxModel<String> ranksModel = new DefaultComboBoxModel<String>();
    	
    	// Determine correct profession to pass into the loop
    	int profession = person.getProfession();
    	while (campaign.getRanks().isEmptyProfession(profession) && profession != Ranks.RPROF_MW) {
    		profession = campaign.getRanks().getAlternateProfession(profession);
    	}
    	
        for(Rank rank : campaign.getRanks().getAllRanks()) {
        	int p = profession;
        	// Grab rank from correct profession as needed
        	while (rank.getName(p).startsWith("--") && p != Ranks.RPROF_MW) {
            	if (rank.getName(p).equals("--")) {
            		p = campaign.getRanks().getAlternateProfession(p);
            	} else if (rank.getName(p).startsWith("--")) {
            		p = campaign.getRanks().getAlternateProfession(rank.getName(p));
            	}
        	}
        	if (rank.getName(p).equals("-")) {
        		continue;
        	}
        	
        	ranksModel.addElement(rank.getName(p));
        }
        choiceRanks.setModel(ranksModel);
        choiceRanks.setSelectedIndex(0);
    }
}
