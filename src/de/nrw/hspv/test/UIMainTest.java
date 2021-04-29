package de.nrw.hspv.test;

public class UIMainTest {

	public static void main(String[] args) {
		Ui startingWindow = new Ui();
		int i=startingWindow.getToolbar().getComponentCount();
		System.out.println(i);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startingWindow.ChangeToolbarButton(new EKButton("new"));
	}

}
