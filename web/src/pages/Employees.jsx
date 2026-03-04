import { useState, useEffect } from 'react';
import { employeeApi, departmentApi } from '../services/api';

const GENDER_OPTIONS = [
  { value: 'MALE', label: 'Male' },
  { value: 'FEMALE', label: 'Female' },
  { value: 'OTHERS', label: 'Others' },
];

const MARITAL_OPTIONS = [
  { value: 'SINGLE', label: 'Single' },
  { value: 'MARRIED', label: 'Married' },
];

const initialFormData = {
  firstName: '',
  lastName: '',
  phone: '',
  email: '',
  gender: 'MALE',
  nationalId: '',
  passportId: '',
  citizenship: '',
  maritalStatus: 'SINGLE',
  bloodGroup: '',
  identityMarks: '',
  salary: 0,
  jobTitle: '',
  disability: false,
  bankName: '',
  accNo: '',
  deptId: 0,
};

export default function Employees() {
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [modalOpen, setModalOpen] = useState(false);
  const [editingEmp, setEditingEmp] = useState(null);
  const [formData, setFormData] = useState(initialFormData);

  const fetchEmployees = async () => {
    try {
      setLoading(true);
      const { data } = await employeeApi.getAll();
      setEmployees(data || []);
      setError('');
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Failed to fetch employees');
    } finally {
      setLoading(false);
    }
  };

  const fetchDepartments = async () => {
    try {
      const { data } = await departmentApi.getAll();
      setDepartments(data || []);
    } catch {
      setDepartments([]);
    }
  };

  useEffect(() => {
    fetchEmployees();
    fetchDepartments();
  }, []);

  const openCreateModal = () => {
    setEditingEmp(null);
    setFormData(initialFormData);
    setModalOpen(true);
  };

  const openEditModal = (emp) => {
    setEditingEmp(emp);
    setFormData({
      firstName: emp.firstName || '',
      lastName: emp.lastName || '',
      phone: emp.phone || '',
      email: emp.email || '',
      gender: emp.gender || 'MALE',
      nationalId: emp.nationalId || '',
      passportId: emp.passportId || '',
      citizenship: emp.citizenship || '',
      maritalStatus: emp.maritalStatus || 'SINGLE',
      bloodGroup: emp.bloodGroup || '',
      identityMarks: emp.identityMarks || '',
      salary: emp.salary || 0,
      jobTitle: emp.jobTitle || '',
      disability: emp.disability || false,
      bankName: emp.bankName || '',
      accNo: emp.accNo || '',
      deptId: emp.deptId || 0,
    });
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setEditingEmp(null);
    setFormData(initialFormData);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    const identityMarks = (formData.identityMarks || '').trim();
    if (!identityMarks) {
      setError('Identity Marks is required');
      return;
    }
    const deptId = formData.deptId || (departments[0]?.deptId ?? 0);
    if (!deptId) {
      setError('Please create at least one department first');
      return;
    }
    const payload = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      phone: formData.phone,
      email: formData.email,
      gender: formData.gender,
      maritalStatus: formData.maritalStatus,
      identityMarks,
      salary: formData.salary || 0,
      disability: formData.disability,
      deptId,
    };
    if (formData.nationalId) payload.nationalId = formData.nationalId;
    if (formData.passportId) payload.passportId = formData.passportId;
    if (formData.citizenship) payload.citizenship = formData.citizenship;
    if (formData.bloodGroup) payload.bloodGroup = formData.bloodGroup;
    if (formData.jobTitle) payload.jobTitle = formData.jobTitle;
    if (formData.bankName) payload.bankName = formData.bankName;
    if (formData.accNo) payload.accNo = formData.accNo;
    try {
      if (editingEmp) {
        await employeeApi.update(editingEmp.empId, payload);
      } else {
        await employeeApi.create(payload);
      }
      closeModal();
      fetchEmployees();
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Operation failed');
    }
  };

  const handleDelete = async (empId) => {
    if (!window.confirm('Are you sure you want to delete this employee?')) return;
    try {
      await employeeApi.delete(empId);
      fetchEmployees();
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Delete failed');
    }
  };

  const getDeptName = (deptId) => {
    const dept = departments.find((d) => d.deptId === deptId);
    return dept?.deptName || deptId || '-';
  };

  if (loading) {
    return (
      <div className="page">
        <div className="spinner" />
        <p>Loading employees...</p>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1>Employees</h1>
        <button type="button" onClick={openCreateModal} className="btn btn-primary">
          Add Employee
        </button>
      </div>
      {error && <div className="alert alert-error">{error}</div>}
      <div className="table-container">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Job Title</th>
              <th>Department</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees.length === 0 ? (
              <tr>
                <td colSpan={7}>No employees found</td>
              </tr>
            ) : (
              employees.map((emp) => (
                <tr key={emp.empId}>
                  <td>{emp.empId}</td>
                  <td>{`${emp.firstName || ''} ${emp.lastName || ''}`.trim() || '-'}</td>
                  <td>{emp.email || '-'}</td>
                  <td>{emp.phone || '-'}</td>
                  <td>{emp.jobTitle || '-'}</td>
                  <td>{getDeptName(emp.deptId)}</td>
                  <td>
                    <button
                      type="button"
                      onClick={() => openEditModal(emp)}
                      className="btn btn-sm btn-outline"
                    >
                      Edit
                    </button>
                    <button
                      type="button"
                      onClick={() => handleDelete(emp.empId)}
                      className="btn btn-sm btn-danger"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {modalOpen && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal modal-lg" onClick={(e) => e.stopPropagation()}>
            <h2>{editingEmp ? 'Edit Employee' : 'Add Employee'}</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="firstName">First Name *</label>
                  <input
                    id="firstName"
                    value={formData.firstName}
                    onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="lastName">Last Name *</label>
                  <input
                    id="lastName"
                    value={formData.lastName}
                    onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
                    required
                  />
                </div>
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="email">Email *</label>
                  <input
                    id="email"
                    type="email"
                    value={formData.email}
                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="phone">Phone *</label>
                  <input
                    id="phone"
                    value={formData.phone}
                    onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
                    required
                  />
                </div>
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="jobTitle">Job Title</label>
                  <input
                    id="jobTitle"
                    value={formData.jobTitle}
                    onChange={(e) => setFormData({ ...formData, jobTitle: e.target.value })}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="deptId">Department</label>
                  <select
                    id="deptId"
                    value={formData.deptId}
                    onChange={(e) =>
                      setFormData({ ...formData, deptId: parseInt(e.target.value, 10) || 0 })
                    }
                  >
                    <option value={0}>-- Select --</option>
                    {departments.map((d) => (
                      <option key={d.deptId} value={d.deptId}>
                        {d.deptName}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="gender">Gender</label>
                  <select
                    id="gender"
                    value={formData.gender}
                    onChange={(e) => setFormData({ ...formData, gender: e.target.value })}
                  >
                    {GENDER_OPTIONS.map((o) => (
                      <option key={o.value} value={o.value}>
                        {o.label}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="maritalStatus">Marital Status</label>
                  <select
                    id="maritalStatus"
                    value={formData.maritalStatus}
                    onChange={(e) => setFormData({ ...formData, maritalStatus: e.target.value })}
                  >
                    {MARITAL_OPTIONS.map((o) => (
                      <option key={o.value} value={o.value}>
                        {o.label}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="salary">Salary</label>
                  <input
                    id="salary"
                    type="number"
                    min={0}
                    value={formData.salary || ''}
                    onChange={(e) =>
                      setFormData({ ...formData, salary: parseInt(e.target.value, 10) || 0 })
                    }
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="nationalId">National ID</label>
                  <input
                    id="nationalId"
                    value={formData.nationalId}
                    onChange={(e) => setFormData({ ...formData, nationalId: e.target.value })}
                  />
                </div>
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="identityMarks">Identity Marks *</label>
                  <input
                    id="identityMarks"
                    type="text"
                    value={formData.identityMarks}
                    onChange={(e) => setFormData({ ...formData, identityMarks: e.target.value })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="citizenship">Citizenship</label>
                  <input
                    id="citizenship"
                    value={formData.citizenship}
                    onChange={(e) => setFormData({ ...formData, citizenship: e.target.value })}
                  />
                </div>
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label className="checkbox-label">
                    <input
                      type="checkbox"
                      checked={formData.disability}
                      onChange={(e) => setFormData({ ...formData, disability: e.target.checked })}
                    />
                    Disability
                  </label>
                </div>
              </div>
              <div className="modal-actions">
                <button type="button" onClick={closeModal} className="btn btn-outline">
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                  {editingEmp ? 'Update' : 'Create'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
