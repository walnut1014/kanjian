package name.walnut.kanjian.app.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public enum PhotoContext {
	
	INSTANCE;
	
	public void init(String path) {
		
		Collection<File> files = FileUtils.listFiles(new File(path), new AndFileFilter(HiddenFileFilter.VISIBLE, 
				new SuffixFileFilter(".jpg")), HiddenFileFilter.VISIBLE);
		photoPaths = new ArrayList<>();
		
		for(File file : files){
			photoPaths.add(file.getPath());
		}
	}

	public List<String> getPhotoPaths() {
		return photoPaths;
	}

	public void setPhotoPaths(List<String> photoPaths) {
		this.photoPaths = photoPaths;
	}
	
	private List<String> photoPaths;
}
