package mpavlovic.sandbox;

import java.util.ArrayList;
import java.util.List;

public class UseCase {
	private String _title;
	private String _description;
	private String _expectedResult;
	private boolean _automated;
	private List<String> _steps;
	
	public UseCase(String title) {
		this._title = title;
		this._description = "";
		this._expectedResult = "";
		this._automated = false;
		this._steps = new ArrayList<String>();
	}
	
	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_description() {
		return _description;
	}

	public void set_description(String _description) {
		this._description = _description;
	}

	public String get_expectedResult() {
		return _expectedResult;
	}

	public void set_expectedResult(String _expectedResult) {
		this._expectedResult = _expectedResult;
	}

	public boolean is_automated() {
		return _automated;
	}

	public void set_automated(boolean _automated) {
		this._automated = _automated;
	}

	public List<String> get_steps() {
		return _steps;
	}

	public void set_steps(List<String> _steps) {
		this._steps = _steps;
	}
}
